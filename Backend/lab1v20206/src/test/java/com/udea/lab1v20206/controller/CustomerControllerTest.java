package com.udea.lab1v20206.controller;

import com.udea.lab1v20206.DTO.CustomerDTO;
import com.udea.lab1v20206.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @Test
    void getAllCustomers_devuelve200ConLaLista() {
        CustomerDTO dto = new CustomerDTO(1L, "Diego", "Botia", "100000", 55400.0);
        when(customerService.getAllCustomers()).thenReturn(List.of(dto));

        ResponseEntity<List<CustomerDTO>> response = customerController.getAllCustomers();

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getCustomerById_devuelve200ConElCliente() {
        CustomerDTO dto = new CustomerDTO(1L, "Diego", "Botia", "100000", 55400.0);
        when(customerService.getCustomerById(1L)).thenReturn(dto);

        ResponseEntity<CustomerDTO> response = customerController.getCustomerById(1L);

        assertEquals(200, response.getStatusCode().value());
        assertSame(dto, response.getBody());
    }

    @Test
    void createCustomer_conBalanceNulo_lanzaExcepcion() {
        CustomerDTO dto = new CustomerDTO(null, "Ana", "Ruiz", "300000", null);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> customerController.createCustomer(dto));

        assertEquals("Balance cannot be null", ex.getMessage());
        verifyNoInteractions(customerService);
    }
}