CREATE TYPE trading_point_type AS ENUM ('DEPARTMENT_STORE', 'SHOP', 'KIOSK', 'STALL');

CREATE TABLE IF NOT EXISTS trading_point
(
    id           SERIAL PRIMARY KEY,
    address      VARCHAR(120)       NOT NULL UNIQUE,
    rent_payment NUMERIC(10, 2)     NOT NULL,
    size         INTEGER            NOT NULL,
    tp_type      trading_point_type NOT NULL,
    tp_info_id   INTEGER            NOT NULL,

    UNIQUE (tp_info_id, tp_type)
);

CREATE TABLE IF NOT EXISTS trading_point_info
(
    id               SERIAL PRIMARY KEY,
    trading_point_id INTEGER     NOT NULL,
    name             VARCHAR(50) NOT NULL,

    UNIQUE (trading_point_id, name)
);

CREATE TABLE IF NOT EXISTS department_trading_point_info
(
    id               SERIAL PRIMARY KEY,
    trading_point_id INTEGER     NOT NULL,
    name             VARCHAR(50) NOT NULL,
    department_id    INTEGER REFERENCES department (id),

    UNIQUE (trading_point_id, name)
);

CREATE TABLE IF NOT EXISTS department
(
    id               SERIAL PRIMARY KEY,
    trading_point_id INTEGER     NOT NULL,
    name             VARCHAR(50) NOT NULL,
    floor            INTEGER     NOT NULL,
    manager_id       INTEGER     NOT NULL,

    UNIQUE (trading_point_id, name)
);

CREATE TABLE IF NOT EXISTS vendor
(
    id SERIAL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS product
(
    article   VARCHAR(40) PRIMARY KEY,
    name      VARCHAR(40),
    photo_url VARCHAR(120)
);

CREATE TABLE IF NOT EXISTS employee
(
    id             SERIAL PRIMARY KEY,
    job_title      VARCHAR(30) NOT NULL,
    marketplace_id INTEGER     NOT NULL,

    FOREIGN KEY (marketplace_id) REFERENCES marketplace (id)
);

CREATE TABLE IF NOT EXISTS product_order
(
    id                 BIGSERIAL PRIMARY KEY,
    status             VARCHAR(20) NOT NULL,
    vendor_id          INTEGER,
    marketplace_id     INTEGER     NOT NULL,
    manager_id         INTEGER,
    requested_products JSONB,
    ordered_products   JSONB,
    requested_at       TIMESTAMP,
    ordered_at         TIMESTAMP,

    FOREIGN KEY (vendor_id) REFERENCES vendor (id),
    FOREIGN KEY (marketplace_id) REFERENCES marketplace (id),
    FOREIGN KEY (manager_id) REFERENCES employee (id)
);

CREATE TABLE IF NOT EXISTS sale
(
    id              SERIAL PRIMARY KEY,
    marketplace_id  INTEGER     NOT NULL,
    product_article VARCHAR(40) NOT NULL,
    customer_info   JSONB,
    created_at      TIMESTAMP DEFAULT NOW(),

    FOREIGN KEY (marketplace_id) REFERENCES marketplace (id),
    FOREIGN KEY (product_article) REFERENCES product (article)
);

CREATE TABLE IF NOT EXISTS product_price
(
    vendor_id       INTEGER        NOT NULL,
    product_article VARCHAR(40)    NOT NULL,
    price           NUMERIC(20, 4) NOT NULL,

    PRIMARY KEY (vendor_id, product_article),

    FOREIGN KEY (product_article) REFERENCES product (article),
    FOREIGN KEY (vendor_id) REFERENCES vendor (id)
);

CREATE TABLE IF NOT EXISTS product_storage
(
    id              BIGSERIAL PRIMARY KEY,
    marketplace_id  INTEGER     NOT NULL,
    product_article VARCHAR(40) NOT NULL,
    delivered_at    TIMESTAMP,
    sold_at         TIMESTAMP,

    FOREIGN KEY (marketplace_id) REFERENCES marketplace (id),
    FOREIGN KEY (product_article) REFERENCES product (article)
);
