package ua.malysh.controller;

import ua.malysh.domain.Product;

public interface ProductService {
    Long save(Product product);

    Product find(Long productId);

    Long delete(Long productId);
}
