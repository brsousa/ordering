package com.algaworks.algashop.ordering.domain.exception;

import com.algaworks.algashop.ordering.domain.vo.id.OrderId;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.ERROR_ORDER_DELIVERY_DATE_CANNOT_BE_IN_THE_PAST;

public class OrderInvalidShippingDeliveryDateException extends RuntimeException {

    public OrderInvalidShippingDeliveryDateException(OrderId orderId) {
        super(String.format(ERROR_ORDER_DELIVERY_DATE_CANNOT_BE_IN_THE_PAST, orderId));
    }
}
