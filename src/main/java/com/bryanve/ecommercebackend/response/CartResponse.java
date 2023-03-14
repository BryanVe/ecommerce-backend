package com.bryanve.ecommercebackend.response;

import com.bryanve.ecommercebackend.model.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record CartResponse(@JsonProperty("id") int id,
                           @NotEmpty @JsonProperty("products") List<Product> products) {

    public int getId() {
        return id;
    }

    public List<Product> getProducts() {
        return products;
    }

}
