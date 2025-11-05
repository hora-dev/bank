package com.rht.bank.infraestructure.persistence;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Schema(name = "bank", description = "bank object saving the id and name of a bank")
@Entity
@Table(name = "bank")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankEntity implements Serializable {

    @Schema(description = "The id of the bank")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "The name of the bank")
    @Column(nullable = false, unique = true)
    @NonNull
    private String name;
}