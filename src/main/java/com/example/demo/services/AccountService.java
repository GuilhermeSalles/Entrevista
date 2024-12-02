package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dtos.AccountDTO;
import com.example.demo.entities.Account;
import com.example.demo.repositories.AccountRepository;
import com.example.demo.services.exceptions.ResourceNotFoundException;

@Service
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Transactional
	public Account createAccount(AccountDTO accountDTO) {
		Account account = new Account(null, accountDTO.getAccountNumber(), accountDTO.getOwnerName(),
				accountDTO.getBalance());
		return accountRepository.save(account);
	}

	@Transactional
	public Account creditAccount(Long accountId, Double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("O valor do crédito deve ser positivo.");
		}

		Account account = accountRepository.findById(accountId)
				.orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada com o ID: " + accountId));

		account.setBalance(account.getBalance() + amount);
		return accountRepository.save(account);
	}

	@Transactional
	public Account debitAccount(Long accountId, Double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("O valor do débito deve ser positivo.");
		}

		Account account = accountRepository.findById(accountId)
				.orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada com o ID: " + accountId));

		if (account.getBalance() < amount) {
			throw new IllegalArgumentException("Saldo insuficiente para realizar o débito.");
		}

		account.setBalance(account.getBalance() - amount);
		return accountRepository.save(account);
	}

	@Transactional
	public void transfer(Long originAccountId, Long destinationAccountId, Double amount) {
		if (amount <= 0) {
			if (amount <= 0) {
				throw new IllegalArgumentException("O valor da transferência deve ser positivo.");
			}

			Account originAccount = accountRepository.findByIdWithLock(originAccountId);
			Account destinationAccount = accountRepository.findByIdWithLock(destinationAccountId);

			if (originAccount.getBalance() < amount) {
				throw new IllegalArgumentException("Saldo insuficiente na conta de origem.");
			}

			originAccount.setBalance(originAccount.getBalance() - amount);
			destinationAccount.setBalance(destinationAccount.getBalance() + amount);

			accountRepository.save(originAccount);
			accountRepository.save(destinationAccount);
		}
	}

}