CREATE TABLE products
(
    id       BIGINT,
    name     TEXT NOT NULL,
    measure  TEXT NOT NULL,
    category TEXT,
    price    NUMERIC,
    CONSTRAINT pk_products_id PRIMARY KEY (id),
    CONSTRAINT uq_products_name UNIQUE (name)
);