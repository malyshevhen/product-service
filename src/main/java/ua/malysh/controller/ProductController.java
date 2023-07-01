package ua.malysh.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Product>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @NotNull
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Long> add(@RequestBody @NotNull @Valid Product product) {
        return new ResponseEntity<>(service.save(product), HttpStatus.CREATED);
    }

    @NotNull
    @GetMapping("/{productId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Product> find(@PathVariable @NotNull Long productId) {
        var retrievedProduct = service.findById(productId);
        return new ResponseEntity<>(retrievedProduct, HttpStatus.OK);
    }

    @NotNull
    @DeleteMapping("/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> delete(@PathVariable @NotNull Long productId) {
        var deletedProductId = service.deleteById(productId);
        return new ResponseEntity<>(deletedProductId, HttpStatus.OK);
    }
}
