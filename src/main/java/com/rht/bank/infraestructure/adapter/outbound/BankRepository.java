package com.rht.bank.infraestructure.adapter.outbound;

import com.rht.bank.infraestructure.persistence.BankEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<BankEntity, Long> {
    Page<BankEntity> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
