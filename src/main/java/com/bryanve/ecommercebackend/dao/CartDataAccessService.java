package com.bryanve.ecommercebackend.dao;

import com.bryanve.ecommercebackend.model.Cart;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository("cartDAO")
public class CartDataAccessService implements CartDAO {

    private static List<Cart> cartList = new ArrayList<>();
    private final Timer timer = new Timer();

    @Override
    public Cart createCart(Cart cart) {
        // * Validate if all product IDs exists
        final List<Integer> productIDs = cart.getProductIDs();
        final List<Integer> notFoundIDs = productIDs
                .stream()
                .filter(productID -> ProductDataAccessService.productsList
                        .stream()
                        .noneMatch(p -> p.getId() == productID))
                .collect(Collectors.toList());

        if (!notFoundIDs.isEmpty()) {
            final List<String> notFoundIDsAsStrings = notFoundIDs
                    .stream()
                    .map(Object::toString)
                    .collect(Collectors.toList());
            final String concatenatedIDs = notFoundIDsAsStrings
                    .stream()
                    .reduce("", (partialString, element) -> partialString + element + ", ");

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Next product ids does not exists: " + concatenatedIDs
            );
        }

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

    @Override
    public Optional<Cart> getCartByID(int id) {
        final List<Cart> matches = cartList.stream().filter(c -> c.getId() == id
        ).collect(Collectors.toList());

        if (matches.isEmpty()) {
            return Optional.empty();
        }

        return Optional.ofNullable(matches.get(0));
    }

}
