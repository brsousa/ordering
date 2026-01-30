package com.algaworks.algashop.ordering;

import com.algaworks.algashop.ordering.domain.entity.Customer;
import com.algaworks.algashop.ordering.domain.utility.IdGenerator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public class CustomerTest {

    // @Test
    public void testingCustomer(){
        Customer customer = new Customer(
                "Bruno",
                IdGenerator.generateTimeBasedUUID(),
                LocalDate.of(1992, 7, 28),
                "bruno@test.com",
                "123412345",
                "1234567890",
                true,
                OffsetDateTime.now()
        );

        System.out.println(customer.id());
    }
}
