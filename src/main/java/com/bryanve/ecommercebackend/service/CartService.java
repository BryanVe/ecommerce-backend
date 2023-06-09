package com.bryanve.ecommercebackend.service;

import com.bryanve.ecommercebackend.dao.CartDAO;
import com.bryanve.ecommercebackend.model.Cart;
import com.bryanve.ecommercebackend.response.CartResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    private final CartDAO cartDAO;

    @Autowired
    public CartService(@Qualifier("cartDAO") CartDAO cartDAO) {
        this.cartDAO = cartDAO;
    }

    public CartResponse createCart(Cart cart) {
        return cartDAO.createCart(cart);
    }

    public boolean deleteCartByID(int id) {
        return cartDAO.deleteCartByID(id);
    }

    public Optional<CartResponse> getCartByID(int id) {
        return cartDAO.getCartByID(id);
    }
}
