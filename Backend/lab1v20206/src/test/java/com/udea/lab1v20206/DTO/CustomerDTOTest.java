package com.udea.lab1v20206.DTO;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerDTOTest {

    @Test
    void settersYGetters_guardanYDevuelvenLosValores() {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(1L);
        dto.setFirstName("Diego");
        dto.setLastName("Botia");
        dto.setAccountNumber("100000");
        dto.setBalance(55400.0);

        assertEquals(1L, dto.getId());
        assertEquals("Diego", dto.getFirstName());
        assertEquals("Botia", dto.getLastName());
        assertEquals("100000", dto.getAccountNumber());
        assertEquals(55400.0, dto.getBalance());
    }

    @Test
    void constructorConParametros_asignaTodosLosCampos() {
        // CustomerDTO(id, firstName, lastName, accountNumber, balance)
        CustomerDTO dto = new CustomerDTO(2L, "Daniel", "Durango", "200000", 14600.0);

        assertEquals(2L, dto.getId());
        assertEquals("Daniel", dto.getFirstName());
        assertEquals("Durango", dto.getLastName());
        assertEquals("200000", dto.getAccountNumber());
        assertEquals(14600.0, dto.getBalance());
    }
}