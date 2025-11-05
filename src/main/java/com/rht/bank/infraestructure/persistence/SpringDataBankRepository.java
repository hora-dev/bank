package com.rht.bank.infraestructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataBankRepository extends JpaRepository<BankEntity, Long> {
}
