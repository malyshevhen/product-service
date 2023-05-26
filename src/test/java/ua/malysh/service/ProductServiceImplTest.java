package ua.malysh.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.malysh.controller.ProductService;
import ua.malysh.domain.Category;
import ua.malysh.domain.Measure;
import ua.malysh.domain.Product;
import ua.malysh.service.exceptions.ProductAlreadyExistsException;
import ua.malysh.service.exceptions.ProductNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.NONE,
        properties = "spring.datasource.url=jdbc:tc:postgresql:15:///test_db"
)
@Transactional
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductServiceImplTest {

    @Autowired
    private ProductService productService;

    @Test
    void shouldSaveUniqueProductInDB() {
        var product = new Product("Test Product", Category.MEAT, Measure.KILOGRAM);
        var id = productService.save(product);
        assertNotNull(id);
    }

    @Test
    void shouldThrowExceptionIfProductWithSameNameIsAlreadyExistsInDB() {
        var product = new Product("Test Product", Category.MEAT, Measure.KILOGRAM);
        productService.save(product);
        assertThrows(ProductAlreadyExistsException.class,
                () -> productService.save(product),
                "Product with name: Test product already exists!");
    }

    @Test
    void shouldRetrieveExistingProductFromDB() {
        var product = new Product("Test Product", Category.MEAT, Measure.KILOGRAM);
        var id = productService.save(product);
        var sameProduct = productService.findById(id);
        product.setId(id);
        assertEquals(product, sameProduct);
    }

    @Test
    void whenFindShouldThrowExceptionIfProductIsNotExistsInDB() {
        assertThrows(ProductNotFoundException.class,
                () -> productService.findById(1L),
                "Product with id: 1 not found!");
    }

    @Test
    void shouldDeleteExistingProductFromDB() {
        var product = new Product("Test Product", Category.MEAT, Measure.KILOGRAM);
        var id = productService.save(product);
        productService.deleteById(id);
        assertThrows(ProductNotFoundException.class,
                () -> productService.findById(id),
                String.format("Product with id: %d not found!", id));
    }

    @Test
    void whenDeleteShouldThrowExceptionIfProductIsNotExistsInDB() {
        assertThrows(ProductNotFoundException.class,
                () -> productService.deleteById(1L),
                "Product with id: 1 not found!");
    }
}