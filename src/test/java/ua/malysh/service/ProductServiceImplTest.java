package ua.malysh.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import ua.malysh.controller.ProductService;
import ua.malysh.domain.Category;
import ua.malysh.domain.NutritionalValue;
import ua.malysh.domain.Product;
import ua.malysh.dto.ProductCreateForm;
import ua.malysh.service.exceptions.ProductAlreadyExistsException;
import ua.malysh.service.exceptions.ProductNotFoundException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE,
        properties = "spring.datasource.url=jdbc:tc:postgresql:15:///test_db")
@Transactional
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductServiceImplTest {

    @Autowired
    private ProductService productService;

    private static Product product;
    private static ProductCreateForm productDto;

    @BeforeAll
    static void setup() {
        product = new Product("Test Product", Category.MEAT);

        var nutritionalValue = new NutritionalValue();
        nutritionalValue.setEnergie(300D);
        nutritionalValue.setCarbohydrates(50D);
        nutritionalValue.setProtein(20D);
        nutritionalValue.setFat(15D);

        product.setNutritionalValue(nutritionalValue);

        productDto = new ProductCreateForm(
                product.getName(),
                product.getCategory().toString(),
                product.getNutritionalValue().getEnergie(),
                product.getNutritionalValue().getCarbohydrates(),
                product.getNutritionalValue().getFat(),
                product.getNutritionalValue().getProtein(),
                product.getDescription(),
                product.getPictureUrl());
    }

    @Test
    void shouldSaveUniqueProductInDB() {
        var id = productService.save(productDto);
        assertNotNull(id);
    }

    @Test
    void shouldThrowExceptionIfProductWithSameNameIsAlreadyExistsInDB() {
        productService.save(productDto);
        assertThrows(ProductAlreadyExistsException.class,
                () -> productService.save(productDto),
                "Product with name: Test product already exists!");
    }

    @Test
    void shouldRetrieveExistingProductFromDB() {
        var id = productService.save(productDto);
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
        var id = productService.save(productDto);
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