package com.bryanve.ecommercebackend.dao;

import com.bryanve.ecommercebackend.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository("productDAO")
public class ProductDataAccessService implements ProductDAO {

    protected static final List<Product> productsList = new ArrayList<>();

    public static List<Product> getProductsFromIDs(List<Integer> ids) {
        return ids.stream().map(i -> {
            final Optional<Product> productMatch = ProductDataAccessService.productsList
                    .stream()
                    .filter(p -> p.getId() == i).findFirst();

            return productMatch.get();
        }).collect(Collectors.toList());
    }

    public static void validateProductIDs(List<Integer> ids) {
        final List<Integer> notFoundIDs = ids
                .stream()
                .filter(productID -> productsList
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
    }

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

    @Override
    public List<Product> getProducts() {
        return productsList;
    }

}
