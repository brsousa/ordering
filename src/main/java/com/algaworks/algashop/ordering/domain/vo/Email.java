package com.algaworks.algashop.ordering.domain.vo;

import com.algaworks.algashop.ordering.domain.exception.ErrorMessages;
import com.algaworks.algashop.ordering.domain.validator.FieldValidations;

import java.util.Objects;

public record Email(
        String value
) {

    public Email(String value) {
        Objects.requireNonNull(value, "Email value must not be null");
        FieldValidations.requiresValidEmail(value, ErrorMessages.VALIDATION_ERROR_EMAIL_IS_INVALID);
        this.value = value.trim();
    }

    @Override
    public String toString() {
        return value;
    }
}
