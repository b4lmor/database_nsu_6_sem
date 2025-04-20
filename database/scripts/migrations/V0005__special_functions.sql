CREATE OR REPLACE PROCEDURE add_department_to_trading_point(
    p_department_id INTEGER,
    p_trading_point_id INTEGER
)
    LANGUAGE plpgsql
AS $$
DECLARE
    v_tp_type trading_point_type;
    v_already_linked_department_id INTEGER;
BEGIN
    -- Проверяем, что торговая точка не принадлежит другому департаменту
    SELECT department_id INTO v_already_linked_department_id
    FROM department_to_trading_point
    WHERE tp_id = p_trading_point_id;

    IF v_already_linked_department_id IS NOT NULL THEN
        RAISE EXCEPTION 'Торговая точка уже принадлежит департаменту %', v_already_linked_department_id;
    END IF;

    -- Получаем тип здания торговой точки
    SELECT tpb.tp_type INTO v_tp_type
    FROM trading_point tp
             JOIN trading_point_building tpb ON tp.tpb_id = tpb.id
    WHERE tp.id = p_trading_point_id;

    -- Проверяем, что здание торговой точки типа DEPARTMENT_STORE
    IF v_tp_type != 'DEPARTMENT_STORE' THEN
        RAISE EXCEPTION 'Торговая точка не находится в здании типа DEPARTMENT_STORE';
    END IF;

    -- Проверяем, что департамент существует
    PERFORM 1 FROM department WHERE id = p_department_id;
    IF NOT FOUND THEN
        RAISE EXCEPTION 'Департамент с ID % не существует', p_department_id;
    END IF;

    -- Проверяем, что торговая точка существует
    PERFORM 1 FROM trading_point WHERE id = p_trading_point_id;
    IF NOT FOUND THEN
        RAISE EXCEPTION 'Торговая точка с ID % не существует', p_trading_point_id;
    END IF;

    -- Если все проверки пройдены, добавляем связь
    INSERT INTO department_to_trading_point (tp_id, department_id)
    VALUES (p_trading_point_id, p_department_id);

    RAISE NOTICE 'Связь между торговой точкой % и департаментом % успешно добавлена', p_trading_point_id, p_department_id;
END;
$$;

-- Функция для подтверждения заказа (установка confirm_date в текущее время)
CREATE OR REPLACE PROCEDURE confirm_order(order_id BIGINT)
AS $$
BEGIN
    UPDATE product_order
    SET confirm_date = NOW()
    WHERE id = order_id;

    IF NOT FOUND THEN
        RAISE EXCEPTION 'Заказ с ID % не найден', order_id;
    END IF;
END;
$$ LANGUAGE plpgsql;

-- Функция для принятия заказа (установка delivery_date в текущее время)
CREATE OR REPLACE PROCEDURE accept_order(order_id BIGINT)
AS $$
BEGIN
    UPDATE product_order
    SET delivery_date = NOW()
    WHERE id = order_id;

    IF NOT FOUND THEN
        RAISE EXCEPTION 'Заказ с ID % не найден', order_id;
    END IF;
END;
$$ LANGUAGE plpgsql;
