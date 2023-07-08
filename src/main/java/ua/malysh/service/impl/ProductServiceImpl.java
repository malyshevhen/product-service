package ua.malysh.service.impl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.malysh.domain.Product;
import ua.malysh.repository.ProductRepository;
import ua.malysh.service.ProductService;
import ua.malysh.service.exceptions.ProductAlreadyExistsException;
import ua.malysh.service.exceptions.ProductNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;

    @Override
    public Long save(@NotNull @Valid Product product) {
        if (ifExists(product))
            throw new ProductAlreadyExistsException("Product with this name already exists!");
        Product savedProduct = repository.save(product);

        return savedProduct.getId();
    }

    @Transactional(readOnly = true)
    @Override
    public Product getById(@NotNull Long productId) {
        return repository.findById(productId)
                .orElseThrow(notFoundSupplier());
    }

    @Override
    public Long deleteById(@NotNull Long productId) {
        var product = getById(productId);
        repository.delete(product);

        return productId;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Product> getAll() {
        return repository.findAll();
    }

    private Supplier<? extends ProductNotFoundException> notFoundSupplier() {
        return () -> new ProductNotFoundException("Product not found!");
    }

    private boolean ifExists(Product product) {
        return repository.findByName(product.getName())
                .isPresent();
    }
}
