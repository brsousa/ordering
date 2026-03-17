package com.algaworks.algashop.ordering.domain.entity;

import java.util.Arrays;
import java.util.List;

public enum OrderStatus {
    DRAFT,
    PLACED(DRAFT),
    PAID(PLACED),
    READY(PAID),
    CANCELLED(PAID, PLACED, READY, DRAFT);

    OrderStatus(OrderStatus... previousStatus) {
        this.previousStatus = Arrays.asList(previousStatus);
    }

    private final List<OrderStatus> previousStatus;

    public boolean canChangeTo(OrderStatus newStatus){
        OrderStatus currentStatus = this;
        return newStatus.previousStatus.contains(currentStatus);
    }

    public boolean canNotChangeTo(OrderStatus status){
        return !canChangeTo(status);
    }
}
