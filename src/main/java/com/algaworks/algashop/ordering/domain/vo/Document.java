package com.algaworks.algashop.ordering.domain.vo;

import java.util.Objects;

public record Document(
        String value
) {

    public  Document(String value) {
        Objects.requireNonNull(value);

        if (value.isBlank()) throw new IllegalArgumentException("Document value must not be blank");

        this.value = value.trim();
    }

    @Override
    public String toString() {
        return value;
    }
}
