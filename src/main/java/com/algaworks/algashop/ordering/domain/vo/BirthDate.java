package com.algaworks.algashop.ordering.domain.vo;

import com.algaworks.algashop.ordering.domain.exception.ErrorMessages;

import java.time.LocalDate;
import java.util.Objects;

public record BirthDate(
        LocalDate birthDate
) {

    public BirthDate(LocalDate birthDate) {
        Objects.requireNonNull(birthDate, ErrorMessages.VALIDATION_ERROR_BIRTHDATE_IS_NULL);

        if (birthDate.isAfter(LocalDate.now())){
            throw new IllegalArgumentException(ErrorMessages.VALIDATION_ERROR_BIRTHDATE_MUST_IN_PAST);
        }

        this.birthDate = birthDate;
    }

    public Integer age(LocalDate birthDate) {
        return LocalDate.now().getYear() - birthDate.getYear();
    }

    @Override
    public String toString() {
        return birthDate.getDayOfYear() + "/" + birthDate.getMonthValue() + "/" + birthDate.getYear();
    }
}
