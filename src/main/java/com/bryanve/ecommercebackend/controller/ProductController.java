package com.bryanve.ecommercebackend.controller;

import com.bryanve.ecommercebackend.model.Product;
import com.bryanve.ecommercebackend.response.ResponseHandler;
import com.bryanve.ecommercebackend.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RequestMapping("/api/v1/product")
@RestController
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Object> createProduct(@Valid @NonNull @RequestBody Product product) {
        final Product createdProduct = productService.createProduct(product);
        return ResponseHandler.responseBuilder("The product was created successfully", HttpStatus.CREATED, createdProduct);
    }

    @GetMapping
    public ResponseEntity<Object> getProducts() {
        final List<Product> products = productService.getProducts();
        String message = "The products were found successfully";

        if (products.isEmpty()) {
            message = "No product was found";
        }

        return ResponseHandler.responseBuilder(message, HttpStatus.OK, products);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Object> deleteProductByID(@Positive(message = "The id must be greater than 0") @PathVariable("id") int id) {
        final boolean foundProduct = productService.deleteProductByID(id);

        if (!foundProduct) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Product with id " + id + " was not found"
            );
        }

        return ResponseHandler.responseBuilder("The product was deleted successfully", HttpStatus.OK, Map.of("productID", id));
    }

}
