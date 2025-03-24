CREATE TYPE trading_point_type AS ENUM (
    'DEPARTMENT_STORE',
    'SHOP',
    'KIOSK',
    'STALL'
    );

CREATE TYPE job_title_type AS ENUM (
    'MANAGER',
    'SALESMAN',
    'CASHIER',
    'WAREHOUSE_WORKER',
    'ACCOUNTANT',
    'SECURITY_GUARD',
    'CLEANER'
    );

CREATE TYPE product_order_status_type AS ENUM (
    'ORDERED',
    'CONFIRMED',
    'DELIVERED'
    );

CREATE TABLE IF NOT EXISTS trading_point_building
(
    id      SERIAL PRIMARY KEY,
    address VARCHAR(120)       NOT NULL UNIQUE,
    tp_type trading_point_type NOT NULL,
    name    VARCHAR(50)        NOT NULL
);

CREATE TABLE IF NOT EXISTS employee
(
    id               SERIAL PRIMARY KEY,
    manager_id       INTEGER REFERENCES employee (id),
    job_title        job_title_type NOT NULL,
    tp_id            INTEGER, -- REFERENCES trading_point (id);
    full_name        VARCHAR(100)   NOT NULL,
    birth_date       DATE           NOT NULL,
    hire_date        DATE           NOT NULL DEFAULT NOW(),
    resignation_date DATE
);

CREATE TABLE IF NOT EXISTS trading_point
(
    id           SERIAL PRIMARY KEY,
    tpb_id       INTEGER REFERENCES trading_point_building (id),
    manager_id   INTEGER REFERENCES employee (id),
    name         VARCHAR(50)    NOT NULL,
    rent_payment NUMERIC(10, 2) NOT NULL,
    tp_size      NUMERIC(10, 2) NOT NULL,

    UNIQUE (tpb_id, name)
);

ALTER TABLE IF EXISTS employee
    ADD CONSTRAINT fk_employee_tp FOREIGN KEY (tp_id) REFERENCES trading_point (id);

CREATE TABLE IF NOT EXISTS department
(
    id         SERIAL PRIMARY KEY,
    tpb_id     INTEGER     NOT NULL REFERENCES trading_point_building (id),
    name       VARCHAR(50) NOT NULL,
    floor      INTEGER     NOT NULL,
    manager_id INTEGER     NOT NULL REFERENCES employee (id),

    UNIQUE (tpb_id, name)
);

CREATE TABLE IF NOT EXISTS department_to_trading_point
(
    tp_id         INTEGER REFERENCES trading_point (id) UNIQUE,
    department_id INTEGER REFERENCES department (id),

    PRIMARY KEY (tp_id, department_id)
);

CREATE TABLE IF NOT EXISTS product
(
    article     VARCHAR(60) PRIMARY KEY,
    name        VARCHAR(60),
    description TEXT,
    photo_url   TEXT
);

CREATE TABLE IF NOT EXISTS product_info
(
    id              BIGSERIAL PRIMARY KEY,
    product_article VARCHAR(60)    NOT NULL REFERENCES product (article),
    delivery_date   TIMESTAMP      NOT NULL,
    sell_date       TIMESTAMP,
    price           NUMERIC(10, 2) NOT NULL
);

CREATE TABLE IF NOT EXISTS trading_point_product
(
    id              BIGSERIAL PRIMARY KEY,
    tp_id           INTEGER NOT NULL REFERENCES trading_point (id),
    product_info_id BIGINT  NOT NULL REFERENCES product_info (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS sale
(
    id         BIGSERIAL PRIMARY KEY,
    tp_id      INTEGER NOT NULL REFERENCES trading_point (id),
    created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS sale_to_tpp
(
    sale_id    BIGINT REFERENCES sale (id) ON DELETE CASCADE,
    tpp_id     BIGINT REFERENCES trading_point_product (id),
    sale_count INTEGER NOT NULL DEFAULT 1,

    PRIMARY KEY (sale_id, tpp_id)
);

CREATE TABLE IF NOT EXISTS client_info
(
    id          BIGSERIAL PRIMARY KEY,
    full_name   VARCHAR(100),
    birth_date  DATE,
    height      NUMERIC(4, 1),
    weight      NUMERIC(5, 2),
    specificity TEXT,
    phone       VARCHAR(15),
    email       VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS sale_to_client_info
(
    sale_id        BIGINT REFERENCES sale (id) UNIQUE,
    client_info_id BIGINT REFERENCES client_info (id) ON DELETE CASCADE,

    PRIMARY KEY (sale_id, client_info_id)
);

CREATE TABLE IF NOT EXISTS vendor
(
    id      SERIAL PRIMARY KEY,
    name    VARCHAR(50)  NOT NULL,
    address VARCHAR(120) NOT NULL,

    UNIQUE (name, address)
);

CREATE TABLE IF NOT EXISTS vendor_product
(
    id              BIGSERIAL PRIMARY KEY,
    vendor_id       INTEGER REFERENCES vendor (id) ON DELETE CASCADE,
    product_info_id BIGINT NOT NULL REFERENCES product_info (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS product_order
(
    id            BIGSERIAL PRIMARY KEY,
    manager_id    INTEGER REFERENCES employee (id),
    vendor_id     INTEGER                   REFERENCES vendor (id) ON DELETE SET NULL,
    tp_id         INTEGER                   NOT NULL REFERENCES trading_point (id),
    order_status  product_order_status_type NOT NULL DEFAULT 'ORDERED',
    create_date   TIMESTAMP                 NOT NULL DEFAULT NOW(),
    confirm_date  TIMESTAMP,
    delivery_date TIMESTAMP
);

CREATE TABLE IF NOT EXISTS product_order_details
(
    product_order_id BIGINT      NOT NULL REFERENCES product_order (id) ON DELETE CASCADE,
    product_article  VARCHAR(60) NOT NULL REFERENCES product (article),
    product_count    INTEGER     NOT NULL,

    PRIMARY KEY (product_order_id, product_article)
);
