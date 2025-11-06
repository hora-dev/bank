package com.rht.bank.application.port.in;

import com.rht.bank.domain.model.Bank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BankUseCase {
    Page<Bank> getAllBanks(Pageable pageable);
    Bank getBankById(Long id);
    Bank createBank(Bank bank);
    Bank updateBank(Bank bank);
    void deleteBank(Long id);
    Page<Bank> getBankByName(String text, Pageable pageable);
}
