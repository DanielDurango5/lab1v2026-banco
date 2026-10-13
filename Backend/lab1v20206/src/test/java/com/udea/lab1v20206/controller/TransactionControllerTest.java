package com.udea.lab1v20206.controller;

import com.udea.lab1v20206.DTO.TransactionDTO;
import com.udea.lab1v20206.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    @Test
    void transferMoney_exitoso_devuelve200() {
        TransactionDTO request = new TransactionDTO();
        TransactionDTO saved = new TransactionDTO(1L, "100000", "200000", 300.0, LocalDateTime.now());
        when(transactionService.transferMoney(request)).thenReturn(saved);

        ResponseEntity<?> response = transactionController.transferMoney(request);

        assertEquals(200, response.getStatusCode().value());
        assertSame(saved, response.getBody());
    }

    @Test
    void transferMoney_conError_devuelve400ConElMensaje() {
        TransactionDTO request = new TransactionDTO();
        when(transactionService.transferMoney(request))
                .thenThrow(new IllegalArgumentException("Saldo insuficiente en la cuenta del remitente."));

        ResponseEntity<?> response = transactionController.transferMoney(request);

        assertEquals(400, response.getStatusCode().value());
        assertEquals("Saldo insuficiente en la cuenta del remitente.", response.getBody());
    }

    @Test
    void getTransactionsByAccount_devuelveLaListaDelServicio() {
        TransactionDTO dto = new TransactionDTO(1L, "100000", "200000", 300.0, LocalDateTime.now());
        when(transactionService.getTransactionsForAccount("100000")).thenReturn(List.of(dto));

        List<TransactionDTO> result = transactionController.getTransactionsByAccount("100000");

        assertEquals(1, result.size());
        assertEquals("200000", result.get(0).getReceiverAccountNumber());
    }
}