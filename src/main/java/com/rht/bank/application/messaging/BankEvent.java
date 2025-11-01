package com.rht.bank.application.messaging;

import com.rht.bank.domain.model.Bank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankEvent {
    private String action;
    private Bank bank;
}
