package com.bryanve.ecommercebackend.controller;

import com.bryanve.ecommercebackend.model.Cart;
import com.bryanve.ecommercebackend.response.ResponseHandler;
import com.bryanve.ecommercebackend.service.CartService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

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

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Object> deleteCartByID(@Positive(message = "The id must be greater than 0") @PathVariable("id") int id) {
        final boolean foundCart = cartService.deleteCartByID(id);

        if (!foundCart) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Cart with id " + id + " was not found"
            );
        }

        return ResponseHandler.responseBuilder("The cart was deleted successfully", HttpStatus.OK, Map.of("cartID", id));
    }

}
