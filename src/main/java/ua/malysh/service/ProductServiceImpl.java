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

    private static void throwProductAlreadyExists(Product p) {
        throw new ProductAlreadyExistsException("Product with this name already exists!");
    }

    private static ProductNotFoundException throwProductNotFound() {
        return new ProductNotFoundException("Product with id: %d not found!");
    }

    @Override
    public Long save(Product product) {
        if (ifExists(product)) throwProductAlreadyExists(product);
        Product savedProduct = repository.save(product);

        return savedProduct.getId();
    }

    @Override
    public Product findById(Long productId) {
        return repository.findById(productId)
                .orElseThrow(ProductServiceImpl::throwProductNotFound);
    }

    @Override
    public Long deleteById(Long productId) {
        var product = findById(productId);
        repository.delete(product);

        return productId;
    }

    private boolean ifExists(Product product) {
        return repository.findByName(product.getName())
                .isPresent();
    }
}
