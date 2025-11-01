package com.rht.bank.application.service;

import com.rht.bank.application.messaging.BankEvent;
import com.rht.bank.application.port.in.BankUseCase;
import com.rht.bank.domain.model.Bank;
import com.rht.bank.infraestructure.adapter.outbound.BankRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
@RequiredArgsConstructor
@Slf4j
public class BankServiceImpl implements BankUseCase {

    private final BankRepository bankRepository;

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    public void sendBankEvent(String action, Bank bank) {
        BankEvent event = new BankEvent(action, bank);
        log.info("Sending bank event: {}", event);
        rabbitTemplate.convertAndSend(exchange, routingKey, event);
    }

    public Page<Bank> getAllBanks(Pageable pageable) {
        return bankRepository.findAll(pageable);
    }

    @Cacheable(value = "bank_redis", key = "#id")
    public Bank getBankById(Long id) {
        log.info("Searching bank in data base...");
        return bankRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "bank id " + id + " not found"));
    }

    public Bank createBank(String bankName) {
        Bank bank = new Bank(null, bankName);
        bank = bankRepository.save(bank);
        sendBankEvent("CREATED", bank);
        return bank;
    }

    @CachePut(value = "bank_redis", key = "#id")
    public Bank updateBank(Long id, String bankName) {
        Bank bank = bankRepository.findById(id)
                .orElseThrow( () -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "bank id " + id + " not found") );
        bank.setName(bankName);
        sendBankEvent("UPDATED", bank);
        return bankRepository.save(bank);
    }

    @CacheEvict(value = "bank_redis", key = "#id")
    public void deleteBank(Long id) {
        Bank b = bankRepository.findById(id)
                .orElseThrow( () -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "bank id " + id + " not found") );
        bankRepository.deleteById(id);
        sendBankEvent("DELETED", b);
    }

    public Page<Bank> getBankByName(String name, Pageable pageable) {
        return bankRepository.findByNameContainingIgnoreCase(name, pageable);
    }
}
