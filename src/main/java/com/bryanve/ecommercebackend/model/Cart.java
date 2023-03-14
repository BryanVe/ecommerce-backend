package com.bryanve.ecommercebackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;

import java.time.Instant;
import java.util.List;

public record Cart(@JsonProperty("id") int id,
                   @JsonProperty("createdAt") Instant createdAt,
                   @NotEmpty @JsonProperty("productIDs") List<Integer> productIDs) {

    public int getId() {
        return id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public List<Integer> getProductIDs() {
        return productIDs;
    }

}
