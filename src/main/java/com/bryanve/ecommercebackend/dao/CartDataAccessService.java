package com.bryanve.ecommercebackend.dao;

import com.bryanve.ecommercebackend.model.Cart;
import com.bryanve.ecommercebackend.model.Product;
import com.bryanve.ecommercebackend.response.CartResponse;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository("cartDAO")
public class CartDataAccessService implements CartDAO {

    private static List<Cart> cartList = new ArrayList<>();
    private final Timer timer = new Timer();

    @Override
    public CartResponse createCart(Cart cart) {
        // * Validate if all product IDs exists
        ProductDataAccessService.validateProductIDs(cart.getProductIDs());
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

        final List<Product> products = ProductDataAccessService.getProductsFromIDs(newCart.getProductIDs());
        return new CartResponse(newCart.getId(), products);
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
    public Optional<CartResponse> getCartByID(int id) {
        final Optional<Cart> cartMatch = cartList.stream().filter(c -> c.getId() == id
        ).findFirst();

        if (cartMatch.isEmpty()) {
            return Optional.empty();
        }

        final Cart foundCart = cartMatch.get();
        final List<Product> products = ProductDataAccessService.getProductsFromIDs(foundCart.getProductIDs());
        final CartResponse response = new CartResponse(foundCart.getId(), products);


        return Optional.of(response);
    }

}
