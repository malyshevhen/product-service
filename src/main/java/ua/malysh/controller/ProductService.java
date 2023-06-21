package ua.malysh.controller;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import ua.malysh.domain.Product;
import ua.malysh.dto.ProductCreateForm;

public interface ProductService {
    Long save(@NotNull @Valid ProductCreateForm productDto);

    Product findById(Long productId);

    Long deleteById(Long productId);

    List<Product> getAll();

}
