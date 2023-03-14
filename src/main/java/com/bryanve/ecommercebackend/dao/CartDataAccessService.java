package com.bryanve.ecommercebackend.dao;

import com.bryanve.ecommercebackend.model.Cart;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository("cartDAO")
public class CartDataAccessService implements CartDAO {

    private static List<Cart> cartList = new ArrayList<>();
    private final Timer timer = new Timer();

    @Override
    public Cart createCart(Cart cart) {
        final Optional<Cart> lastCart = cartList.stream().max(Comparator.comparing(Cart::getId));

        AtomicInteger nextID = new AtomicInteger();
        lastCart.ifPresent(c -> {
            nextID.set(c.getId() + 1);
        });

        final int currentID = nextID.get();
        final Cart newCart = new Cart(currentID, cart.getProductIDs());
        cartList.add(newCart);

        // * Add a timer for deleting a cart by id after 10 minutes (TTL)
        int ttl = 10 * 60 * 1000;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                deleteCartByID(currentID);
            }
        }, ttl);

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
