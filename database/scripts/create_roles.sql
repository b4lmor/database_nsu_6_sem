CREATE ROLE employee;
CREATE ROLE manager;
CREATE ROLE vendor;
CREATE ROLE admin WITH SUPERUSER;

CREATE USER employee_11 WITH PASSWORD 'employee_password';
GRANT employee TO employee_11;

CREATE USER employee_1 WITH PASSWORD 'manager_password';
GRANT manager TO employee_1;

CREATE USER vendor_1 WITH PASSWORD 'vendor_password';
GRANT vendor TO vendor_1;

CREATE USER admin_user WITH PASSWORD 'admin_password' SUPERUSER;
