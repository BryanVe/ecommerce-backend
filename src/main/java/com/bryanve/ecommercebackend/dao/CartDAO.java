package com.bryanve.ecommercebackend.dao;

import com.bryanve.ecommercebackend.model.Cart;
import com.bryanve.ecommercebackend.response.CartResponse;

import java.util.Optional;

public interface CartDAO {

    CartResponse createCart(Cart cart);

    boolean deleteCartByID(int id);

    Optional<CartResponse> getCartByID(int id);

}
