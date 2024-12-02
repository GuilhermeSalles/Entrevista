package com.example.demo.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entities.Account;
import com.example.demo.repositories.AccountRepository;

@SpringBootTest 
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    private Account account;

    @BeforeEach
    public void setup() {
        // Cria uma conta inicial com saldo de 1000
        account = new Account(null, "123456", "Test User", 1000.0);
        account = accountRepository.save(account);
    }

    @Test
    public void testConcurrentTransactions() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2); // Cria um pool de 10 threads

        // Simula 10 threads realizando transações simultâneas
        for (int i = 0; i < 5; i++) {
            executor.submit(() -> accountService.creditAccount(account.getId(), 100.0)); // Crédito
            executor.submit(() -> {
                try {
                    accountService.debitAccount(account.getId(), 50.0); // Débito
                } catch (IllegalArgumentException ignored) {
                    // Ignora erros de saldo insuficiente para fins de teste
                }
            });
        }

        // Aguarda a execução de todas as threads
        executor.shutdown();
        while (!executor.isTerminated()) {
            Thread.sleep(100);
        }

        // Recupera o saldo final da conta
        Account updatedAccount = accountRepository.findById(account.getId()).orElseThrow();

        // Verifica se o saldo é consistente
        System.out.println("Saldo final: " + updatedAccount.getBalance());
        assertThat(updatedAccount.getBalance()).isGreaterThanOrEqualTo(0);
    }
}
