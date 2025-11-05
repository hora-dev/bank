package com.rht.bank.application.port.out;

import com.rht.bank.domain.model.Bank;

public interface BankRepositoryPort {

    Bank createBank(Bank bank);
    Bank getBankById(Long id);
    Bank updateBank(Bank bank);
    Bank deleteBank(Long id);
}

