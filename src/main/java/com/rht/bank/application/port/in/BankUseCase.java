package com.rht.bank.application.port.in;

import com.rht.bank.domain.model.Bank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BankUseCase {
    Page<Bank> getAllBanks(Pageable pageable);
    Bank getBankById(Long id);
    Bank createBank(String bankName);
    Bank updateBank(Long id, String bankName);
    void deleteBank(Long id);
}
