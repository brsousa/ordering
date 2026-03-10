package com.algaworks.algashop.ordering.domain.vo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public record Money(
        BigDecimal value
) implements Comparable<Money>{

    public static final BigDecimal ZERO = new BigDecimal("0");
    public static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_EVEN;

    public Money(String value){
        this(new BigDecimal(value));
    }

    public Money(BigDecimal value) {
        Objects.requireNonNull(value);
        this.value = value.setScale(2, ROUNDING_MODE);

        if (this.value.signum() == -1) {
            throw new IllegalArgumentException("value must be greater than zero");
        }
    }

    public Money add(Money addValue) {
        Objects.requireNonNull(addValue);
        return new Money(this.value.add(addValue.value));
    }

    public Money divide(Money divideValue) {
        return new  Money(this.value.divide(divideValue.value,ROUNDING_MODE));
    }

    public Money multiply(Quantity quantity) {
        Objects.requireNonNull(quantity);

        if (quantity.value() < 1){
            throw new IllegalArgumentException("value must be greater than zero");
        }

        BigDecimal multiplyValueValue = this.value.multiply(new BigDecimal(quantity.value()));

        return new Money(multiplyValueValue);
    }

    @Override
    public String toString() {
        return this.value.toString();
    }

    @Override
    public int compareTo(Money o) {
        return this.value.compareTo(o.value());
    }
}
