package com.algaworks.algashop.ordering.domain.vo;

import java.util.Objects;

public record ZipCode(String value) {

    public ZipCode(String value) {
        Objects.requireNonNull(value, "Zip Code value must not be null");

        if (value.isBlank()) {
            throw new IllegalArgumentException("Zip Code value must not be blank");
        }

        if (value.length() != 5) {
            throw new IllegalArgumentException("Zip Code value must have 5 characters");
        }

        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
