package com.rht.bank.application.port.out;

import com.rht.bank.domain.model.Bank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BankRepositoryPort {

    Bank createBank(Bank bank);
    Bank getBankById(Long id);
    Bank updateBank(Bank bank);
    Bank deleteBank(Long id);
    Page<Bank> findAllByPages(Pageable pageable);

    Page<Bank> getBankByName(String text, Pageable pageable);
}

