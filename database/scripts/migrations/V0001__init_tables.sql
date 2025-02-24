CREATE TABLE IF NOT EXISTS marketplace
(
    id    SERIAL PRIMARY KEY,
    mtype VARCHAR(30) NOT NULL,
    info  JSONB       NOT NULL
);

CREATE TABLE IF NOT EXISTS vendor
(
    id SERIAL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS product
(
    article VARCHAR(40) PRIMARY KEY
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
