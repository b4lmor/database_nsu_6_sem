-- Заполнение таблицы trading_point_building
COPY trading_point_building (id, address, tp_type, name)
    FROM '/volumes/trading_point_building.csv'
    WITH (FORMAT csv, HEADER true, DELIMITER ',', ENCODING 'UTF8');

-- Заполнение таблицы employee
COPY employee (id, full_name, birth_date, hire_date, resignation_date)
    FROM '/volumes/employee.csv'
    WITH (FORMAT csv, HEADER true, DELIMITER ',', ENCODING 'UTF8');

-- Заполнение таблицы trading_point
COPY trading_point (id, tpb_id, manager_id, name, rent_payment, tp_size)
    FROM '/volumes/trading_point.csv'
    WITH (FORMAT csv, HEADER true, DELIMITER ',', ENCODING 'UTF8');

-- Заполнение таблицы temp_job
COPY job (id, employee_id, tp_id, job_title, start_date, end_date, salary)
    FROM '/volumes/job.csv'
    WITH (FORMAT csv, HEADER true, DELIMITER ',', ENCODING 'UTF8');

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
