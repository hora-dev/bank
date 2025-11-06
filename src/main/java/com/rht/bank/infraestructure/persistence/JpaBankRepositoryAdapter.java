package com.rht.bank.infraestructure.persistence;

import com.rht.bank.application.port.out.BankRepositoryPort;
import com.rht.bank.domain.model.Bank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;

@Repository
@RequiredArgsConstructor
@Slf4j
public class JpaBankRepositoryAdapter implements BankRepositoryPort {

    private final SpringDataBankRepository springDataBankRepository;

    @Override
    public Bank createBank(Bank bank) {
        BankEntity bankEntity = new BankEntity(null, bank.name());
        BankEntity savedBank = springDataBankRepository.save( bankEntity );
        return new Bank(savedBank.getId(), savedBank.getName());
    }

    @Override
    @Cacheable(value = "bank_redis", key = "#id")
    public Bank getBankById(Long id) {
        log.info("Searching bank in data base...");
        BankEntity bankEntity = springDataBankRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "bank id " + id + " not found"));
        return new Bank(bankEntity.getId(), bankEntity.getName());
    }

    @Override
    @CachePut(value = "bank_redis", key = "#bank.id")
    public Bank updateBank(Bank bank) {
        log.info("Searching bank in data base...");
        BankEntity bankEntity = springDataBankRepository.findById(bank.id())
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "bank id " + bank.id() + " not found"));
        bankEntity.setName( bank.name() );
        BankEntity savedEntity = springDataBankRepository.save(bankEntity);
        return new Bank(savedEntity.getId(), savedEntity.getName());
    }

    @Override
    public Bank deleteBank(Long id) {
        BankEntity b = springDataBankRepository.findById(id)
                .orElseThrow( () -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "bank id " + id + " not found") );
        springDataBankRepository.deleteById(id);
        return new Bank( b.getId(), b.getName());
    }

    @Override
    public Page<Bank> findAllByPages(Pageable pageable) {
        return springDataBankRepository.findAll(pageable)
                .map(bankEntity -> new Bank(bankEntity.getId(), bankEntity.getName()));
    }

    @Override
    public Page<Bank> getBankByName(String text, Pageable pageable) {
        return springDataBankRepository.findByNameContainingIgnoreCase(text, pageable)
                .map(bankEntity -> new Bank(bankEntity.getId(), bankEntity.getName()));
    }
}
