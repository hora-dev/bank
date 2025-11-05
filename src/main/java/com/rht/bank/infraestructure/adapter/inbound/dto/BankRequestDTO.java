package com.rht.bank.infraestructure.adapter.inbound.dto;

import java.io.Serializable;

public record BankRequestDTO(
        Long id,
        String name
) implements Serializable {
}
