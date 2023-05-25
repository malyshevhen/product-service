package ua.malysh.service;

import lombok.RequiredArgsConstructor;
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
        var name = product.getName();

        repository.findByName(name)
                .ifPresent(p -> {
                    throw new ProductAlreadyExistsException(
                            String.format("Product with name: %s already exists!", name));
                });
        Product savedProduct = repository.save(product);

        return savedProduct.getId();
    }

    @Override
    public Product find(Long productId) {
        return repository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(
                        String.format("Product with id: %d not found!", productId)));
    }

    @Override
    public Long delete(Long productId) {
        repository
                .findById(productId)
                .ifPresentOrElse(
                        product -> repository.deleteById(productId),
                        () -> {
                            throw new ProductNotFoundException(
                                    String.format("Product with id: %d not found!", productId));
                        });
        return productId;
    }
}
