package com.bryanve.ecommercebackend.dao;

import com.bryanve.ecommercebackend.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Repository("productDAO")
public class ProductDataAccessService implements ProductDAO {

    private static final List<Product> productsList = new ArrayList<>();

    @Override
    public Product createProduct(Product product) {
        final Optional<Product> lastProduct = productsList.stream().max(Comparator.comparing(Product::getId));

        AtomicInteger nextIndex = new AtomicInteger();
        lastProduct.ifPresent(p -> {
            nextIndex.set(p.getId() + 1);
        });

        final Product newProduct = new Product(nextIndex.get(), product.getName(), product.getDescription(), product.getAmount());
        productsList.add(newProduct);
        return newProduct;
    }

}
