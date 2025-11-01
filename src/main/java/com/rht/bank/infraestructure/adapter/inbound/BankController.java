package com.rht.bank.infraestructure.adapter.inbound;

import com.rht.bank.application.service.BankServiceImpl;
import com.rht.bank.domain.model.Bank;
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

    @Operation(summary = "Get all banks available")
    @GetMapping
    public ResponseEntity<Page<Bank>> getAllBanks(Pageable pageable) {
        return ResponseEntity.ok(bankService.getAllBanks(pageable));
    }

    @Operation(summary = "Get a bank searching by its id")
    @GetMapping("{id}")
    public ResponseEntity<Bank> getBankById(@PathVariable Long id) {
        return ResponseEntity.ok(bankService.getBankById(id));
    }

    @Operation(summary = "Get all banks containg a text on their name ")
    @GetMapping("/search/name")
    public ResponseEntity<Page<Bank>> getBankByName(@RequestParam String text, Pageable pageable) {
        return ResponseEntity.ok(bankService.getBankByName(text, pageable));
    }


    @Operation(summary = "Create a bank and save it on db")
    @PostMapping
    public ResponseEntity<Bank> createBank(@RequestBody String bankName) {
        return ResponseEntity.ok(bankService.createBank(bankName));
    }

    @Operation(summary = "Update a bank searching by its id")
    @PutMapping("{id}")
    public ResponseEntity<Bank> updateBank(@PathVariable Long id, @RequestBody String bankName) {
        return ResponseEntity.ok(bankService.updateBank(id, bankName));
    }

    @Operation(summary = "Delete a bank searching by its id")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteBank(@PathVariable Long id) {
        bankService.deleteBank(id);
        return ResponseEntity.noContent().build();
    }
}