ALTER TABLE product_info
    ADD CONSTRAINT check_price_non_negative CHECK (price >= 0),
    ADD CONSTRAINT check_date_order CHECK (sell_date IS NULL OR sell_date > delivery_date);

ALTER TABLE employee
    ADD CONSTRAINT check_date_order CHECK (resignation_date > hire_date),
    ADD CONSTRAINT check_employee_adult CHECK (EXTRACT(YEAR FROM AGE(hire_date, birth_date)) >= 18);

ALTER TABLE product_order_details
    ADD CONSTRAINT check_product_count_non_negative CHECK (product_count >= 0);

ALTER TABLE client_info
    ADD CONSTRAINT chk_height_range CHECK (height > 0 AND height <= 300),
    ADD CONSTRAINT chk_weight_range CHECK (weight > 0 AND weight <= 500),
    ADD CONSTRAINT uniq_phone UNIQUE (phone),
    ADD CONSTRAINT uniq_email UNIQUE (email),
    ADD CONSTRAINT chk_email_format CHECK (email ~ '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$');

ALTER TABLE product_order
    ADD CONSTRAINT chk_date_order
        CHECK (
            (confirm_date IS NULL OR confirm_date >= create_date) AND
            (delivery_date IS NULL OR (confirm_date IS NOT NULL AND delivery_date >= confirm_date))
            );

ALTER TABLE job
    ADD CONSTRAINT valid_dates CHECK (end_date IS NULL OR end_date > start_date),
    ADD CONSTRAINT positive_salary CHECK (salary > 0);

-- 1. Триггер для автоматического обновления статуса заказа

CREATE OR REPLACE FUNCTION update_order_status()
    RETURNS TRIGGER AS
$$
BEGIN
    IF NEW.confirm_date IS NOT NULL AND NEW.order_status = 'ORDERED' THEN
        NEW.order_status := 'CONFIRMED';

    ELSIF NEW.delivery_date IS NOT NULL AND NEW.order_status = 'CONFIRMED' THEN
        NEW.order_status := 'DELIVERED';
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_update_order_status
    BEFORE UPDATE
    ON product_order
    FOR EACH ROW
EXECUTE FUNCTION update_order_status();

-- 2. Триггер для проверки, что отдел принадлежит зданию торговой точки

CREATE OR REPLACE FUNCTION check_department_building()
    RETURNS TRIGGER AS
$$
BEGIN
    IF NOT EXISTS (SELECT 1
                   FROM trading_point_building
                   WHERE id = (SELECT tpb_id FROM department WHERE id = NEW.department_id)
                     AND id = (SELECT tpb_id FROM trading_point WHERE id = NEW.tp_id)) THEN
        RAISE EXCEPTION 'Department must belong to the same building as the trading point';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_check_department_building
    BEFORE INSERT OR UPDATE
    ON department_to_trading_point
    FOR EACH ROW
EXECUTE FUNCTION check_department_building();

-- 3. Триггер для проверки, что поставщик имеет достаточное кол-во товара

CREATE OR REPLACE FUNCTION check_vendor_product_availability()
    RETURNS TRIGGER AS
$$
DECLARE
    original_vendor_id   INTEGER;
    vendor_product_count INTEGER;
BEGIN
    IF (SELECT vendor_id INTO original_vendor_id FROM product_order WHERE id = NEW.product_order_id) IS NOT NULL THEN

        SELECT COUNT(*)
        INTO vendor_product_count
        FROM vendor_product
        WHERE vendor_product.reserved_by IS NULL
          AND vendor_id = original_vendor_id
          AND product_info_id = (SELECT id FROM product_info WHERE product_article = NEW.product_article);

        IF vendor_product_count < NEW.product_count THEN
            RAISE EXCEPTION 'Not enough products available from the vendor (Vendor ID: %, Product Article: %, Required: %, Available: %)',
                original_vendor_id,
                NEW.product_article,
                NEW.product_count,
                vendor_product_count;
        END IF;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_check_vendor_product_availability
    BEFORE INSERT OR UPDATE
    ON product_order_details
    FOR EACH ROW
EXECUTE FUNCTION check_vendor_product_availability();

-- 4. Триггер для проверки, что ТП, привязанная к секции - часть универмага

CREATE OR REPLACE FUNCTION check_tp_is_department_store()
    RETURNS TRIGGER AS
$$
BEGIN
    IF NOT EXISTS (SELECT 1
                   FROM trading_point tp
                            JOIN trading_point_building tpb ON tp.tpb_id = tpb.id
                   WHERE tp.id = NEW.tp_id
                     AND tpb.tp_type = 'DEPARTMENT_STORE') THEN
        RAISE EXCEPTION 'Trading point (ID: %) is not a department store', NEW.tp_id;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_check_tp_is_department_store
    BEFORE INSERT OR UPDATE
    ON department_to_trading_point
    FOR EACH ROW
EXECUTE FUNCTION check_tp_is_department_store();

-- 5. Триггер для установки end_date для старой должности при добавлении новой

CREATE OR REPLACE FUNCTION update_previous_job_end_date()
    RETURNS TRIGGER AS
$$
BEGIN
    UPDATE job
    SET end_date = NEW.start_date
    WHERE employee_id = NEW.employee_id
      AND end_date IS NULL
      AND id != NEW.id;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_set_previous_job_end_date
    BEFORE INSERT OR UPDATE
    ON job
    FOR EACH ROW
EXECUTE FUNCTION update_previous_job_end_date();

-- 6. Триггер для установки manager_id в null при изменении/удалении роли менеджера

CREATE OR REPLACE FUNCTION clear_manager_id_on_role_change()
    RETURNS TRIGGER AS
$$
BEGIN
    IF (TG_OP = 'UPDATE' AND OLD.job_title::text = 'MANAGER' AND
        (NEW.job_title::text != 'MANAGER' OR NEW.tp_id != OLD.tp_id)) OR
       (TG_OP = 'DELETE' AND OLD.job_title::text = 'MANAGER') THEN

        UPDATE trading_point
        SET manager_id = NULL
        WHERE manager_id = OLD.employee_id;

    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_clear_manager_id_trigger
    AFTER UPDATE OR DELETE
    ON job
    FOR EACH ROW
EXECUTE FUNCTION clear_manager_id_on_role_change();

-- 7. Триггер для установки reserved_by у vendor_id при изменении статуса product_order на CONFIRMED

CREATE OR REPLACE FUNCTION update_vendor_product()
    RETURNS TRIGGER AS $$
BEGIN
    IF NEW.order_status = 'CONFIRMED' AND (OLD.order_status IS DISTINCT FROM NEW.order_status) THEN

        -- Обновляем vendor_product.reserved_by для всех товаров в этом заказе
        UPDATE vendor_product vp
        SET reserved_by = NEW.id
        FROM product_order_details pod
                 JOIN product_info pi ON pod.product_article = pi.product_article
        WHERE pod.product_order_id = NEW.id
          AND vp.product_info_id = pi.id
          AND vp.vendor_id = NEW.vendor_id
          AND vp.reserved_by IS NULL;

    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_product_order_status_changed
    BEFORE UPDATE ON product_order
    FOR EACH ROW
EXECUTE FUNCTION update_vendor_product();

