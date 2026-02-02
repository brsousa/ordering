package com.algaworks.algashop.ordering.domain.exception;

public class CustomerArchivedException extends DomainException{

    public CustomerArchivedException(String message) {
        super(ErrorMessages.ERROR_CUSTOMER_ARCHIVED);
    }

    public CustomerArchivedException(String message, Throwable cause) {
        super(ErrorMessages.ERROR_CUSTOMER_ARCHIVED, cause);
    }
}
