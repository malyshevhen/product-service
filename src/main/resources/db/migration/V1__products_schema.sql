CREATE TABLE products
(
    product_id       BIGSERIAL,
    product_name     TEXT NOT NULL,
    product_measure  TEXT NOT NULL,
    product_category TEXT,
    CONSTRAINT pk_products_id PRIMARY KEY (product_id),
    CONSTRAINT uq_products_name UNIQUE (product_name)
);