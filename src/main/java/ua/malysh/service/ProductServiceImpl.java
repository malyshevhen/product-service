package ua.malysh.service;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.malysh.controller.ProductService;
import ua.malysh.domain.Product;
import ua.malysh.repository.ProductRepository;
import ua.malysh.service.exceptions.ProductAlreadyExistsException;
import ua.malysh.service.exceptions.ProductNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;

    @Override
    public Long save(Product product) {
        if (ifExists(product))
            throw new ProductAlreadyExistsException("Product with this name already exists!");
        Product savedProduct = repository.save(product);

        return savedProduct.getId();
    }

    @Override
    public Product findById(Long productId) {
        return repository.findById(productId)
                .orElseThrow(notFoundSupplier());
    }

    @Override
    public Long deleteById(Long productId) {
        var product = findById(productId);
        repository.delete(product);

        return productId;
    }

    @Override
    public List<Product> getAll() {
        return repository.findAll();
    }

    private Supplier<? extends ProductNotFoundException> notFoundSupplier() {
        return () -> new ProductNotFoundException("Product with id: %d not found!");
    }

    private boolean ifExists(Product product) {
        return repository.findByName(product.getName())
                .isPresent();
    }
}
