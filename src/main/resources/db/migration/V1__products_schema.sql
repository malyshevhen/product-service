CREATE TABLE products
(
    product_id          BIGSERIAL,
    product_name        TEXT NOT NULL,
    product_measure     TEXT NOT NULL,
    product_category    TEXT NOT NULL,
    product_description TEXT,
    product_picture_url TEXT,
    created_at          TIMESTAMP,
    modified_at         TIMESTAMP,
    CONSTRAINT pk_products_id PRIMARY KEY (product_id),
    CONSTRAINT uq_products_name UNIQUE (product_name)
);