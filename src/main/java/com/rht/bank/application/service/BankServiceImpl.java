package com.rht.bank.application.service;

import com.rht.bank.application.messaging.BankEvent;
import com.rht.bank.application.port.in.BankUseCase;
import com.rht.bank.application.port.out.BankRepositoryPort;
import com.rht.bank.domain.model.Bank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BankServiceImpl implements BankUseCase {

    private final BankRepositoryPort bankRepositoryPort;

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
        return bankRepositoryPort.findAllByPages(pageable);
    }

    @Override
    public Bank getBankById(Long id) {
        return bankRepositoryPort.getBankById(id);
    }

    public Bank createBank(Bank bank) {
        Bank createdBank = bankRepositoryPort.createBank(bank);
        sendBankEvent("CREATED", createdBank);
        return createdBank;
    }

    @Override
    public Bank updateBank(Bank bank) {
        Bank updatedBank = bankRepositoryPort.updateBank(bank);
        sendBankEvent("UPDATED", updatedBank);
        return bank;
    }


    @CacheEvict(value = "bank_redis", key = "#id")
    public void deleteBank(Long id) {
        Bank deletedBank = bankRepositoryPort.deleteBank(id);
        sendBankEvent("DELETED", deletedBank);
    }

    @Override
    public Page<Bank> getBankByName(String text, Pageable pageable) {
        return bankRepositoryPort.getBankByName(text, pageable);
    }
}
