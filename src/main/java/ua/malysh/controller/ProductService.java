package ua.malysh.controller;

import java.util.List;

import ua.malysh.domain.Product;

public interface ProductService {
    Long save(Product product);

    Product findById(Long productId);

    Long deleteById(Long productId);

    List<Product> getAll();

}
