package ua.malysh.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import ua.malysh.domain.Product;
import ua.malysh.service.ProductService;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;

    @NotNull
    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @NotNull
    @PostMapping
    public ResponseEntity<Long> add(@RequestBody @NotNull @Valid Product product) {
        return new ResponseEntity<>(service.save(product), HttpStatus.CREATED);
    }

    @NotNull
    @GetMapping("/{productId}")
    public ResponseEntity<Product> find(@PathVariable Long productId) {
        var retrievedProduct = service.findById(productId);
        return new ResponseEntity<>(retrievedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Long> delete(@PathVariable Long productId) {
        var deletedProductId = service.deleteById(productId);
        return new ResponseEntity<>(deletedProductId, HttpStatus.OK);
    }
}
