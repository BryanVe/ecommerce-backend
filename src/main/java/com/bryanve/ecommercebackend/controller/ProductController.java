package com.bryanve.ecommercebackend.controller;

import com.bryanve.ecommercebackend.model.Product;
import com.bryanve.ecommercebackend.response.ResponseHandler;
import com.bryanve.ecommercebackend.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
