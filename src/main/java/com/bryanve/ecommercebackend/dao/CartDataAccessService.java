package com.bryanve.ecommercebackend.dao;

import com.bryanve.ecommercebackend.model.Cart;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository("cartDAO")
public class CartDataAccessService implements CartDAO {

    private static List<Cart> cartList = new ArrayList<>();

    @Override
    public Cart createCart(Cart cart) {
        final Optional<Cart> lastCart = cartList.stream().max(Comparator.comparing(Cart::getId));

        AtomicInteger nextIndex = new AtomicInteger();
        lastCart.ifPresent(c -> {
            nextIndex.set(c.getId() + 1);
        });

        final Instant now = Instant.now();
        final Cart newCart = new Cart(nextIndex.get(), now, cart.getProductIDs());
        cartList.add(newCart);
        return newCart;
    }

    @Override
    public boolean deleteCartByID(int id) {
        final boolean cartFound = cartList.stream().anyMatch(c -> c.getId() == id);

        if (!cartFound) {
            return false;
        }

        cartList = cartList.stream().filter(c -> c.getId() != id
        ).collect(Collectors.toList());

        return true;
    }
}
