package ua.malysh.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.malysh.domain.Product;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;

    @NotNull
    @PostMapping
    public ResponseEntity<Long> add(@NotNull @Valid Product product) {
        return new ResponseEntity<>(service.save(product), HttpStatus.CREATED);
    }

    @NotNull
    @GetMapping("/{productId}")
    public ResponseEntity<Product> find(@PathVariable Long productId) {
        var retrivedProduct = service.findById(productId);
        return new ResponseEntity<>(retrivedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Long> delete(@PathVariable Long productId) {
        var deletedProductId = service.deleteById(productId);
        return new ResponseEntity<>(deletedProductId, HttpStatus.OK);
    }
}
