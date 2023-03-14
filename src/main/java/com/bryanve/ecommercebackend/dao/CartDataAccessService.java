package com.bryanve.ecommercebackend.dao;

import com.bryanve.ecommercebackend.model.Cart;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Repository("cartDAO")
public class CartDataAccessService implements CartDAO {

    private static final List<Cart> cartList = new ArrayList<>();

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
}
