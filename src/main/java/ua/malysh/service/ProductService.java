package ua.malysh.service;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import ua.malysh.domain.Product;

public interface ProductService {

    Long save(@NotNull @Valid Product product);

    Product getById(Long productId);

    Long deleteById(Long productId);

    List<Product> getAll();

}
