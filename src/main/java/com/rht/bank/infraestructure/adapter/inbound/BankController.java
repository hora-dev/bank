package com.rht.bank.infraestructure.adapter.inbound;

import com.rht.bank.application.port.in.BankUseCase;
import com.rht.bank.application.service.BankServiceImpl;
import com.rht.bank.domain.model.Bank;
import com.rht.bank.infraestructure.adapter.inbound.dto.BankRequestDTO;
import com.rht.bank.infraestructure.adapter.inbound.dto.BankResponseDTO;
import com.rht.bank.infraestructure.persistence.BankEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Banks API", description = "CRUD operations related to banks")
@RestController
@RequestMapping("/banks")
@RequiredArgsConstructor
public class BankController {

    private final BankServiceImpl bankService;
    private final BankUseCase bankUseCase;

    @Operation(summary = "Get all banks available")
    @GetMapping
    public ResponseEntity<Page<BankEntity>> getAllBanks(Pageable pageable) {
        return ResponseEntity.ok(bankService.getAllBanks(pageable));
    }

    @Operation(summary = "Get a bank searching by its id")
    @GetMapping("{id}")
    public ResponseEntity<BankResponseDTO> getBankById(@PathVariable Long id) {
        Bank bank = bankUseCase.getBankById(id);
        return ResponseEntity.ok( new BankResponseDTO(bank.id(), bank.name()) );
    }

    @Operation(summary = "Get all banks containg a text on their name ")
    @GetMapping("/search/name")
    public ResponseEntity<Page<BankEntity>> getBankByName(@RequestParam String text, Pageable pageable) {
        //return ResponseEntity.ok(bankService.getBankByName(text, pageable));
        return null;
    }


    @Operation(summary = "Create a bank and save it on db")
    @PostMapping
    public ResponseEntity<BankResponseDTO> createBank(@RequestBody BankRequestDTO bankRequestDTO) {
        Bank bank = bankUseCase.createBank(new Bank(null, bankRequestDTO.name()));
        return ResponseEntity.ok(new BankResponseDTO(bank.id(), bankRequestDTO.name()));
    }

    @Operation(summary = "Update a bank searching by its id")
    @PutMapping("{id}")
    public ResponseEntity<Bank> updateBank(@PathVariable Long id, @RequestBody String bankName) {
        Bank updatedBank = bankUseCase.updateBank( new Bank( id, bankName));
        return ResponseEntity.ok(updatedBank);
    }

    @Operation(summary = "Delete a bank searching by its id")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteBank(@PathVariable Long id) {
        bankUseCase.deleteBank(id);
        return ResponseEntity.noContent().build();
    }
}