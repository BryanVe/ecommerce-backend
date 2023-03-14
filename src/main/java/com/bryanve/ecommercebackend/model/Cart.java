package com.bryanve.ecommercebackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record Cart(@JsonProperty("id") int id,
                   @NotEmpty @JsonProperty("productIDs") List<Integer> productIDs) {

    public int getId() {
        return id;
    }

    public List<Integer> getProductIDs() {
        return productIDs;
    }

}
