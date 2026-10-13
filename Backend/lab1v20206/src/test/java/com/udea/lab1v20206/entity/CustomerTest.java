package com.udea.lab1v20206.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    void settersYGetters_guardanYDevuelvenLosValores() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setAccountNumber("100000");
        customer.setFirstName("Diego");
        customer.setLastName("Botia");
        customer.setBalance(55400.0);

        assertEquals(1L, customer.getId());
        assertEquals("100000", customer.getAccountNumber());
        assertEquals("Diego", customer.getFirstName());
        assertEquals("Botia", customer.getLastName());
        assertEquals(55400.0, customer.getBalance());
    }

    @Test
    void constructorConParametros_asignaTodosLosCampos() {
        // Customer(id, accountNumber, firstName, lastName, balance)
        Customer customer = new Customer(2L, "200000", "Daniel", "Durango", 14600.0);

        assertEquals(2L, customer.getId());
        assertEquals("200000", customer.getAccountNumber());
        assertEquals("Daniel", customer.getFirstName());
        assertEquals("Durango", customer.getLastName());
        assertEquals(14600.0, customer.getBalance());
    }

    @Test
    void toString_incluyeLosDatosDelCliente() {
        Customer customer = new Customer(2L, "200000", "Daniel", "Durango", 14600.0);

        String text = customer.toString();

        assertTrue(text.contains("200000"));
        assertTrue(text.contains("Daniel"));
        assertTrue(text.contains("Durango"));
    }
}