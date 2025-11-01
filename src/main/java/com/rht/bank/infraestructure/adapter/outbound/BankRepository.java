package com.rht.bank.infraestructure.adapter.outbound;

import com.rht.bank.domain.model.Bank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {
    Page<Bank> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
