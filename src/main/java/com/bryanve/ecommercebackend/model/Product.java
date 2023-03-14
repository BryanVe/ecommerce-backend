package com.bryanve.ecommercebackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record Product(@JsonProperty("id") int id,
                      @NotBlank @JsonProperty("name") String name,
                      @NotBlank @JsonProperty("description") String description,
                      @Positive(message = "The amount must be greater than 0") @JsonProperty("amount") double amount) {

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

}
