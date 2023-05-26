package ua.malysh.controller;

import ua.malysh.domain.Product;

public interface ProductService {
    Long save(Product product);

    Product findById(Long productId);

    Long deleteById(Long productId);
}
