package com.bryanve.ecommercebackend.controller;

import com.bryanve.ecommercebackend.model.Cart;
import com.bryanve.ecommercebackend.response.ResponseHandler;
import com.bryanve.ecommercebackend.service.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/cart")
@RestController
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<Object> createCart(@Valid @NonNull @RequestBody Cart cart) {
        final Cart createdCart = cartService.createCart(cart);
        return ResponseHandler.responseBuilder("The cart was created successfully", HttpStatus.CREATED, createdCart);
    }

}
