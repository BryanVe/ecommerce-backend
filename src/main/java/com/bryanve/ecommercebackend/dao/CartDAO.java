package com.bryanve.ecommercebackend.dao;

import com.bryanve.ecommercebackend.model.Cart;

import java.util.Optional;

public interface CartDAO {

    Cart createCart(Cart cart);

    boolean deleteCartByID(int id);

    Optional<Cart> getCartByID(int id);

}
