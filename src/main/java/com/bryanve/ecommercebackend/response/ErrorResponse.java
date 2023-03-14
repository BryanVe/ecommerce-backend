package com.bryanve.ecommercebackend.response;

public record ErrorResponse(String message, int status) {
    @Override
    public String toString() {
        return "{\"message\": \"" + message + "\", \"status\": " + status + '}';
    }
}
