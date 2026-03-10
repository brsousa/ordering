package com.algaworks.algashop.ordering.domain.vo;

import com.algaworks.algashop.ordering.domain.validator.FieldValidations;

import java.util.Objects;

public record ProductName(
        String value
) {

    public ProductName {
        FieldValidations.requiresNonBlank(value, "value must not be blank");
    }

    @Override
    public String toString() {
        return this.value;
    }
}
