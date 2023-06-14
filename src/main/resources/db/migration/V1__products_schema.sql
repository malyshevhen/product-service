CREATE TABLE products
(
    product_id          BIGSERIAL,
    product_name        TEXT NOT NULL,
    product_category    TEXT NOT NULL,
    product_description TEXT,
    product_picture_url TEXT,
    energie_value       DOUBLE PRECISION NOT NULL,
    carbohydrates_value DOUBLE PRECISION NOT NULL,
    fat_value           DOUBLE PRECISION NOT NULL,
    protein_value       DOUBLE PRECISION NOT NULL,
    created_at          TIMESTAMP,
    modified_at         TIMESTAMP,
    CONSTRAINT pk_products_id PRIMARY KEY (product_id),
    CONSTRAINT uq_products_name UNIQUE (product_name)
);