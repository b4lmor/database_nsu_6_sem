-- Заполнение таблицы trading_point_building
COPY trading_point_building (id, address, tp_type, name)
    FROM '/volumes/trading_point_building.csv'
    WITH (FORMAT csv, HEADER true, DELIMITER ',', ENCODING 'UTF8');

-- Временная таблица для employee
CREATE TEMP TABLE temp_employee
(
    id               INTEGER PRIMARY KEY,
    manager_id       INTEGER,
    job_title        VARCHAR(50),
    tp_id            INTEGER,
    full_name        VARCHAR(100),
    birth_date       DATE,
    hire_date        DATE,
    resignation_date DATE
);

-- Временная таблица для trading_point
CREATE TEMP TABLE temp_trading_point
(
    id           INTEGER PRIMARY KEY,
    tpb_id       INTEGER,
    manager_id   INTEGER,
    name         VARCHAR(50),
    rent_payment NUMERIC(10, 2),
    tp_size      NUMERIC(10, 2)
);

-- Заполнение таблицы employee
COPY temp_employee (id, manager_id, job_title, tp_id, full_name, birth_date, hire_date, resignation_date)
    FROM '/volumes/employee.csv'
    WITH (FORMAT csv, HEADER true, DELIMITER ',', ENCODING 'UTF8');

-- Заполнение таблицы trading_point
COPY temp_trading_point (id, tpb_id, manager_id, name, rent_payment, tp_size)
    FROM '/volumes/trading_point.csv'
    WITH (FORMAT csv, HEADER true, DELIMITER ',', ENCODING 'UTF8');

-- Вставка данных в employee без tp_id
INSERT INTO employee (id, job_title, full_name, birth_date, hire_date, resignation_date)
SELECT id, job_title::job_title_type, full_name, birth_date, hire_date, resignation_date
FROM temp_employee;

-- Вставка данных в trading_point без manager_id
INSERT INTO trading_point (id, tpb_id, name, rent_payment, tp_size)
SELECT id, tpb_id, name, rent_payment, tp_size
FROM temp_trading_point;

-- Обновление trading_point для установки manager_id
UPDATE trading_point
SET manager_id = temp.manager_id
FROM temp_trading_point temp
WHERE trading_point.id = temp.id;

-- Обновление employee для установки tp_id
UPDATE employee
SET tp_id = temp.tp_id
FROM temp_employee temp
WHERE employee.id = temp.id;

DROP TABLE IF EXISTS temp_employee;
DROP TABLE IF EXISTS temp_trading_point;

-- Заполнение таблицы department
COPY department (id, tpb_id, name, floor, manager_id)
    FROM '/volumes/department.csv'
    WITH (FORMAT csv, HEADER true, DELIMITER ',', ENCODING 'UTF8');

-- Заполнение таблицы department_to_trading_point
COPY department_to_trading_point (tp_id, department_id)
    FROM '/volumes/department_to_trading_point.csv'
    WITH (FORMAT csv, HEADER true, DELIMITER ',', ENCODING 'UTF8');

-- Заполнение таблицы product
COPY product (article, name, description, photo_url)
    FROM '/volumes/product.csv'
    WITH (FORMAT csv, HEADER true, DELIMITER ',', ENCODING 'UTF8');

-- Заполнение таблицы product_info
COPY product_info (id, product_article, delivery_date, sell_date, price)
    FROM '/volumes/product_info.csv'
    WITH (FORMAT csv, HEADER true, DELIMITER ',', ENCODING 'UTF8');

-- Заполнение таблицы trading_point_product
COPY trading_point_product (id, tp_id, product_info_id)
    FROM '/volumes/trading_point_product.csv'
    WITH (FORMAT csv, HEADER true, DELIMITER ',', ENCODING 'UTF8');

-- Заполнение таблицы sale
COPY sale (id, tp_id, created_at)
    FROM '/volumes/sale.csv'
    WITH (FORMAT csv, HEADER true, DELIMITER ',', ENCODING 'UTF8');

-- Заполнение таблицы sale_to_tpp
COPY sale_to_tpp (sale_id, tpp_id, sale_count)
    FROM '/volumes/sale_to_tpp.csv'
    WITH (FORMAT csv, HEADER true, DELIMITER ',', ENCODING 'UTF8');

-- Заполнение таблицы client_info
COPY client_info (id, full_name, birth_date, height, weight, specificity, phone, email)
    FROM '/volumes/client_info.csv'
    WITH (FORMAT csv, HEADER true, DELIMITER ',', ENCODING 'UTF8');

-- Заполнение таблицы sale_to_client_info
COPY sale_to_client_info (sale_id, client_info_id)
    FROM '/volumes/sale_to_client_info.csv'
    WITH (FORMAT csv, HEADER true, DELIMITER ',', ENCODING 'UTF8');

-- Заполнение таблицы vendor
COPY vendor (id, name, address)
    FROM '/volumes/vendor.csv'
    WITH (FORMAT csv, HEADER true, DELIMITER ',', ENCODING 'UTF8');

-- Заполнение таблицы vendor_product
COPY vendor_product (id, vendor_id, product_info_id)
    FROM '/volumes/vendor_product.csv'
    WITH (FORMAT csv, HEADER true, DELIMITER ',', ENCODING 'UTF8');

-- Заполнение таблицы product_order
COPY product_order (id, manager_id, vendor_id, tp_id, order_status, create_date, confirm_date, delivery_date)
    FROM '/volumes/product_order.csv'
    WITH (FORMAT csv, HEADER true, DELIMITER ',', ENCODING 'UTF8');

-- Заполнение таблицы product_order_details
COPY product_order_details (product_order_id, product_article, product_count)
    FROM '/volumes/product_order_details.csv'
    WITH (FORMAT csv, HEADER true, DELIMITER ',', ENCODING 'UTF8');
