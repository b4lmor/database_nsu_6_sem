CREATE ROLE employee;
CREATE ROLE manager;
CREATE ROLE vendor;
CREATE ROLE admin WITH SUPERUSER;

-- 1. trading_point_building
GRANT SELECT ON trading_point_building TO employee, manager, vendor;

-- 2. employee
GRANT ALL PRIVILEGES ON employee TO manager;

-- 3. trading_point
GRANT SELECT ON trading_point TO employee;
GRANT SELECT, UPDATE ON trading_point TO manager;

-- 4. department
GRANT SELECT ON department TO employee, manager;

-- 5. department_to_trading_point
GRANT SELECT ON department_to_trading_point TO employee, manager;

-- 6. product
GRANT SELECT ON product TO employee;
GRANT SELECT, INSERT, UPDATE ON product TO manager;
GRANT SELECT, INSERT ON product TO vendor;

-- 7. product_info
GRANT SELECT ON product_info TO employee;
GRANT ALL PRIVILEGES ON product_info TO manager, vendor;

-- 8. trading_point_product
GRANT SELECT, UPDATE ON trading_point_product TO employee;
GRANT ALL PRIVILEGES ON trading_point_product TO manager;
GRANT SELECT ON trading_point_product TO vendor;

-- 9. sale
GRANT SELECT, INSERT ON sale TO employee, manager;

-- 10. sale_to_tpp
-- GRANT SELECT, INSERT ON sale_to_tpp TO employee, manager;

-- 11. client_info
GRANT SELECT (id), INSERT ON client_info TO employee;
GRANT SELECT, INSERT, UPDATE ON client_info TO manager;

-- 12. sale_to_client_info
-- GRANT SELECT, INSERT ON sale_to_client_info TO employee, manager;

-- 13. vendor
GRANT SELECT ON vendor TO employee, manager;
GRANT SELECT, INSERT, UPDATE ON vendor TO vendor;

-- 14. vendor_product
GRANT SELECT ON vendor_product TO employee, manager;
GRANT ALL PRIVILEGES ON vendor_product TO vendor;

-- 15. product_order
GRANT SELECT, INSERT ON product_order TO employee;
GRANT ALL PRIVILEGES ON product_order TO manager;
GRANT SELECT ON product_order TO vendor;

-- 16. product_order_details
GRANT SELECT, INSERT ON product_order_details TO employee;
GRANT ALL PRIVILEGES ON product_order_details TO manager;
GRANT SELECT ON product_order_details TO vendor;

-- Row-Level Security для таблиц с ограничением по ID

-- 1. vendor
ALTER TABLE vendor
    ENABLE ROW LEVEL SECURITY;

CREATE POLICY vendor_policy ON vendor
    FOR ALL
    TO vendor
    USING (id = CURRENT_USER::INT);
-- имя роли совпадает с ID поставщика

-- 2. vendor_product
ALTER TABLE vendor_product
    ENABLE ROW LEVEL SECURITY;

CREATE POLICY vendor_product_policy ON vendor_product
    FOR ALL
    TO vendor
    USING (vendor_id = CURRENT_USER::INT);

-- 3. product_order
ALTER TABLE product_order
    ENABLE ROW LEVEL SECURITY;

CREATE POLICY product_order_policy ON product_order
    FOR ALL
    TO vendor
    USING (vendor_id = CURRENT_USER::INT);

-- 4. product_order_details
ALTER TABLE product_order_details
    ENABLE ROW LEVEL SECURITY;

CREATE POLICY product_order_details_policy ON product_order_details
    FOR ALL
    TO vendor
    USING (product_order_id IN (SELECT id
                                FROM product_order
                                WHERE vendor_id = CURRENT_USER::INT));

-- 5. employee
ALTER TABLE employee
    ENABLE ROW LEVEL SECURITY;

CREATE POLICY manager_employee_policy ON employee
    FOR ALL
    USING (
    EXISTS (SELECT 1
            FROM job
                     JOIN employee AS manager ON job.employee_id = manager.id
            WHERE manager.id = CURRENT_USER::INT
              AND job.job_title = 'MANAGER'::job_title_type
              AND (job.end_date IS NULL OR job.end_date > CURRENT_DATE)));
