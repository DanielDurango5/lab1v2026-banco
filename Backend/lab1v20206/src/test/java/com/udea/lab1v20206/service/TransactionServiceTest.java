package com.udea.lab1v20206.service;

import com.udea.lab1v20206.DTO.TransactionDTO;
import com.udea.lab1v20206.entity.Customer;
import com.udea.lab1v20206.entity.Transaction;
import com.udea.lab1v20206.repository.CustomerRepository;
import com.udea.lab1v20206.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private TransactionService transactionService;

    private Customer sender;
    private Customer receiver;

    @BeforeEach
    void setUp() {
        // Customer(id, accountNumber, firstName, lastName, balance)
        sender = new Customer(1L, "100000", "Diego", "Botia", 1000.0);
        receiver = new Customer(2L, "200000", "Daniel", "Durango", 500.0);
    }

    private TransactionDTO buildRequest(String from, String to, Double amount) {
        TransactionDTO dto = new TransactionDTO();
        dto.setSenderAccountNumber(from);
        dto.setReceiverAccountNumber(to);
        dto.setAmount(amount);
        return dto;
    }

    @Test
    void transferMoney_conDatosValidos_actualizaSaldosYGuardaTransaccion() {
        when(customerRepository.findByAccountNumber("100000")).thenReturn(Optional.of(sender));
        when(customerRepository.findByAccountNumber("200000")).thenReturn(Optional.of(receiver));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(inv -> {
            Transaction t = inv.getArgument(0);
            t.setId(10L);
            return t;
        });

        TransactionDTO result = transactionService.transferMoney(buildRequest("100000", "200000", 300.0));

        assertEquals(700.0, sender.getBalance());
        assertEquals(800.0, receiver.getBalance());
        assertEquals(10L, result.getId());
        assertEquals(300.0, result.getAmount());
        assertNotNull(result.getTimestamp());
        verify(customerRepository).save(sender);
        verify(customerRepository).save(receiver);
        verify(transactionRepository).save(any(Transaction.class));
    }

    @Test
    void transferMoney_conSaldoInsuficiente_lanzaExcepcionYNoGuarda() {
        when(customerRepository.findByAccountNumber("100000")).thenReturn(Optional.of(sender));
        when(customerRepository.findByAccountNumber("200000")).thenReturn(Optional.of(receiver));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> transactionService.transferMoney(buildRequest("100000", "200000", 5000.0)));

        assertEquals("Saldo insuficiente en la cuenta del remitente.", ex.getMessage());
        assertEquals(1000.0, sender.getBalance());
        assertEquals(500.0, receiver.getBalance());
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void transferMoney_conRemitenteInexistente_lanzaExcepcion() {
        when(customerRepository.findByAccountNumber("999999")).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> transactionService.transferMoney(buildRequest("999999", "200000", 100.0)));

        assertEquals("La cuenta del remitente no existe.", ex.getMessage());
    }

    @Test
    void transferMoney_conReceptorInexistente_lanzaExcepcion() {
        when(customerRepository.findByAccountNumber("100000")).thenReturn(Optional.of(sender));
        when(customerRepository.findByAccountNumber("999999")).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> transactionService.transferMoney(buildRequest("100000", "999999", 100.0)));

        assertEquals("La cuenta del receptor no existe.", ex.getMessage());
    }

    @Test
    void transferMoney_conNumeroDeCuentaNulo_lanzaExcepcion() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> transactionService.transferMoney(buildRequest(null, "200000", 100.0)));

        assertTrue(ex.getMessage().contains("obligatorios"));
        verifyNoInteractions(customerRepository, transactionRepository);
    }

    @Test
    void getTransactionsForAccount_devuelveLasTransaccionesComoDTO() {
        // Transaction(id, sender, receiver, amount, timestamp)
        Transaction t = new Transaction(1L, "100000", "200000", 300.0, LocalDateTime.now());
        when(transactionRepository.findBySenderAccountNumberOrReceiverAccountNumber("100000", "100000"))
                .thenReturn(List.of(t));

        List<TransactionDTO> result = transactionService.getTransactionsForAccount("100000");

        assertEquals(1, result.size());
        assertEquals("100000", result.get(0).getSenderAccountNumber());
        assertEquals("200000", result.get(0).getReceiverAccountNumber());
        assertEquals(300.0, result.get(0).getAmount());
    }
}