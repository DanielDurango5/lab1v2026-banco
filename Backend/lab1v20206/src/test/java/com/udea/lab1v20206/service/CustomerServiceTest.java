package com.udea.lab1v20206.service;

import com.udea.lab1v20206.DTO.CustomerDTO;
import com.udea.lab1v20206.entity.Customer;
import com.udea.lab1v20206.mapper.CustomerMapper;
import com.udea.lab1v20206.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerService customerService;

    // Customer(id, accountNumber, firstName, lastName, balance)
    // CustomerDTO(id, firstName, lastName, accountNumber, balance)

    @Test
    void getAllCustomers_devuelveLaListaMapeadaADTO() {
        Customer customer = new Customer(1L, "100000", "Diego", "Botia", 55400.0);
        CustomerDTO dto = new CustomerDTO(1L, "Diego", "Botia", "100000", 55400.0);
        when(customerRepository.findAll()).thenReturn(List.of(customer));
        when(customerMapper.toDTO(customer)).thenReturn(dto);

        List<CustomerDTO> result = customerService.getAllCustomers();

        assertEquals(1, result.size());
        assertEquals("Diego", result.get(0).getFirstName());
    }

    @Test
    void getCustomerById_cuandoExiste_devuelveElCliente() {
        Customer customer = new Customer(1L, "100000", "Diego", "Botia", 55400.0);
        CustomerDTO dto = new CustomerDTO(1L, "Diego", "Botia", "100000", 55400.0);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customerMapper.toDTO(customer)).thenReturn(dto);

        CustomerDTO result = customerService.getCustomerById(1L);

        assertEquals("100000", result.getAccountNumber());
    }

    @Test
    void getCustomerById_cuandoNoExiste_lanzaExcepcion() {
        when(customerRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> customerService.getCustomerById(99L));

        assertEquals("Cliente no encontrado", ex.getMessage());
    }

    @Test
    void createCustomer_guardaYDevuelveElClienteCreado() {
        CustomerDTO input = new CustomerDTO(null, "Ana", "Ruiz", "300000", 1000.0);
        Customer entity = new Customer(null, "300000", "Ana", "Ruiz", 1000.0);
        Customer saved = new Customer(3L, "300000", "Ana", "Ruiz", 1000.0);
        CustomerDTO output = new CustomerDTO(3L, "Ana", "Ruiz", "300000", 1000.0);
        when(customerMapper.toEntity(input)).thenReturn(entity);
        when(customerRepository.save(entity)).thenReturn(saved);
        when(customerMapper.toDTO(saved)).thenReturn(output);

        CustomerDTO result = customerService.createCustomer(input);

        assertEquals(3L, result.getId());
        verify(customerRepository).save(entity);
    }
}