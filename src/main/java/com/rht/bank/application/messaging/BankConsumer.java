package com.rht.bank.application.messaging;

import com.rht.bank.domain.model.Bank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BankConsumer {

    public static final String PROCESSING_bank = "Processing bank ";

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void handleBankEvent(BankEvent event) {
        log.info("Received bank event: {}", event);

        switch (event.getAction()) {
            case "CREATED":
                handleBankCreated(event.getBank());
                break;
            case "UPDATED":
                handleBankUpdated(event.getBank());
                break;
            case "DELETED":
                handleBankDeleted(event.getBank().getId());
                break;
            default:
                log.warn("Unknown action: {}", event.getAction());
        }
    }

    private void handleBankCreated(Bank bank) {
        log.info(PROCESSING_bank + "created: {}", bank);
    }

    private void handleBankUpdated(Bank bank) {
        log.info(PROCESSING_bank + "updated: {}", bank);
    }

    private void handleBankDeleted(Long id) {
        log.info(PROCESSING_bank + "deleted with ID: {}", id);
    }

    private void handleBankFindAll(Bank bank) {
        log.info(PROCESSING_bank + "find all: {}", bank);
    }
}
