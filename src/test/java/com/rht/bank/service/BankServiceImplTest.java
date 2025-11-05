package com.rht.bank.service;

import com.rht.bank.application.messaging.BankEvent;
import com.rht.bank.application.service.BankServiceImpl;
import com.rht.bank.domain.model.Bank;
import com.rht.bank.infraestructure.persistence.BankEntity;
import com.rht.bank.infraestructure.adapter.outbound.BankRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class BankServiceImplTest {

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private BankRepository bankRepository;

    @Autowired
    private BankServiceImpl bankService;

    private Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank(1L, "HSBC");
    }

   /* @Test
    void getAllBanks_ShouldReturnPagedResults() {
        Pageable pageable = PageRequest.of(0, 10);
        when(bankRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(bank)));

        Page<BankEntity> result = bankService.getAllBanks(pageable);

        assertFalse(result.isEmpty());
        assertEquals(1, result.getTotalElements());
        verify(bankRepository).findAll(pageable);
    }

    /*@Test
    void getBanksById_WhenExists_ShouldReturnBank() {
        when(bankRepository.findById(1L)).thenReturn(Optional.of(bank));

        BankEntity result = bankService.getBankById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(bankRepository).findById(1L);
    }

    @Test
    void getBankById_WhenNotFound_ShouldThrowException() {
        when(bankRepository.findById(2L)).thenReturn(Optional.empty());

        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> bankService.getBankById(2L));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        verify(bankRepository).findById(2L);
    }*/

    /*@Test
    void createBank_ShouldSaveAndReturnBank() {
        when(bankRepository.save(any(BankEntity.class))).thenReturn(bank);

        BankEntity result = bankService.createBank(bank));

        assertNotNull(result);
        assertEquals("HSBC", result.getName());
        verify(bankRepository).save(any(BankEntity.class));
        verify(rabbitTemplate).convertAndSend(eq("bank.exchange"), eq("bank.routing.key"), any(BankEvent.class));

    }*/

    /*@Test
    void updateBank_WhenExists_ShouldUpdateAndReturnBank() {
        when(bankRepository.findById(1L)).thenReturn(Optional.of(bank));
        when(bankRepository.save(bank)).thenReturn(bank);

        BankEntity result = bankService.updateBank(1L, "bank new name");

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("bank new name", result.getName());
        verify(bankRepository).findById(1L);
        verify(bankRepository).save(bank);
    }

    @Test
    void updateBank_WhenNotFound_ShouldThrowException() {
        when(bankRepository.findById(2L)).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND, "bank id " + 2L + " not found"));

        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> bankService.updateBank(2L, bank.getName()));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        verify(bankRepository).findById(2L);
    }

    @Test
    void deleteBank_WhenExists_ShouldDelete() {
        when(bankRepository.findById(1L)).thenReturn(Optional.of(bank));

        doNothing().when(bankRepository).deleteById(1L);

        assertDoesNotThrow(() -> bankService.deleteBank(1L));
        verify(bankRepository).findById(1L);
        verify(bankRepository).deleteById(1L);
    }

    @Test
    void deleteBank_WhenNotFound_ShouldThrowException() {
        when(bankRepository.findById(2L)).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND, "bank id " + 2L + " not found"));

        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> bankService.deleteBank(2L));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        verify(bankRepository).findById(2L);
    }

    /*@Test
    void getBankByName_ShouldReturnPagedResults() {
        Pageable pageable = PageRequest.of(0, 10);
        when(bankRepository.findByNameContainingIgnoreCase("SB", pageable))
                .thenReturn(new PageImpl<>(List.of(bank)));

        Page<BankEntity> result = bankService.getBankByName("SB", pageable);

        assertFalse(result.isEmpty());
        assertEquals(1, result.getTotalElements());
        verify(bankRepository).findByNameContainingIgnoreCase("SB", pageable);
    }*/
}
