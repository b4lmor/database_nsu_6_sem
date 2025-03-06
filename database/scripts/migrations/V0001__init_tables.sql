CREATE TYPE trading_point_type AS ENUM ('DEPARTMENT_STORE', 'SHOP', 'KIOSK', 'STALL');

CREATE TABLE IF NOT EXISTS trading_point
(
    id      SERIAL PRIMARY KEY,
    address VARCHAR(120) NOT NULL UNIQUE,
    name    VARCHAR(50)  NOT NULL
);

CREATE TABLE IF NOT EXISTS atomic_trading_point (
    id           SEQUENCE PRIMARY KEY,
    tp_id        INTEGER  REFERENCES trading_point(id),
    tp_type      trading_point_type NOT NULL,
    tp_info_id   INTEGER            NOT NULL,
    name         VARCHAR(50)        NOT NULL,
    rent_payment NUMERIC(10, 2)     NOT NULL,
    tp_size      INTEGER            NOT NULL,

    UNIQUE (tp_id, tp_type),
    UNIQUE (tp_id, name)
);

CREATE TABLE IF NOT EXISTS department_to_atomic_trading_point(
    atp_id        INTEGER REFERENCES atomic_trading_point(id),
    department_id INTEGER REFERENCES department(id),

    PRIMARY KEY (atp_id, department_id),
    UNIQUE (atp_id, department)
);

CREATE TABLE IF NOT EXISTS department
(
    id               SERIAL PRIMARY KEY,
    tp_id            INTEGER     NOT NULL REFERENCES trading_point(id),
    name             VARCHAR(50) NOT NULL,
    floor            INTEGER     NOT NULL,
    manager_id       INTEGER     NOT NULL,

    UNIQUE (tp_id, name)
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
