package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dtos.AccountDTO;
import com.example.demo.entities.Account;
import com.example.demo.services.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@PostMapping
	public ResponseEntity<Account> createAccount(@RequestBody AccountDTO accountDTO) {
		Account newAccount = accountService.createAccount(accountDTO);
		return ResponseEntity.ok(newAccount);
	}

	@PatchMapping("/{id}/credit")
	public ResponseEntity<Account> creditAccount(@PathVariable Long id, @RequestParam Double amount) {
		Account updatedAccount = accountService.creditAccount(id, amount);
		return ResponseEntity.ok(updatedAccount);
	}

	@PatchMapping("/{id}/debit")
	public ResponseEntity<Account> debitAccount(@PathVariable Long id, @RequestParam Double amount) {
		Account updatedAccount = accountService.debitAccount(id, amount);
		return ResponseEntity.ok(updatedAccount);
	}

	@PostMapping("/{originId}/transfer/{destinationId}")
	public ResponseEntity<String> transfer(@PathVariable Long originId, @PathVariable Long destinationId,
			@RequestParam Double amount) {
		accountService.transfer(originId, destinationId, amount);
		return ResponseEntity.ok("Transferência realizada com sucesso.");
	}

}