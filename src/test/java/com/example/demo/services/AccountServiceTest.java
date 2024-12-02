package com.example.demo.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entities.Account;
import com.example.demo.repositories.AccountRepository;

@SpringBootTest
class AccountServiceTest {

	@Autowired
	private AccountService accountService;

	@Autowired
	private AccountRepository accountRepository;

	private Account accountOrigin;
	private Account accountDestination;

	@BeforeEach
	void setup() {
		// Criação de contas para teste
		accountOrigin = accountRepository.save(new Account(null, "123456", "Origin User", 1000.0));
		accountDestination = accountRepository.save(new Account(null, "654321", "Destination User", 500.0));
	}

	@Test
	@DisplayName("Testa múltiplas transferências concorrentes garantindo consistência nos saldos")
	void testConcurrentTransfers() throws InterruptedException {
		int numberOfTransfers = 5; // Número de transferências para simular
		double transferAmount = 100.0; // Valor de cada transferência

		ExecutorService executor = Executors.newFixedThreadPool(5); // Pool de threads

		// Simular múltiplas transferências
		for (int i = 0; i < numberOfTransfers; i++) {
			executor.submit(
					() -> accountService.transfer(accountOrigin.getId(), accountDestination.getId(), transferAmount));
			System.out.println("Transferiu: " + (i + 1) + "vezes");
		}

		// Finalizar execução das threads
		executor.shutdown();
		while (!executor.isTerminated()) {
			Thread.sleep(100);
		}

		// Recuperar saldos após as transferências
		Account updatedOrigin = accountRepository.findById(accountOrigin.getId()).orElseThrow();
		Account updatedDestination = accountRepository.findById(accountDestination.getId()).orElseThrow();

		// Saldo esperado
		double expectedOriginBalance = accountOrigin.getBalance() - (numberOfTransfers * transferAmount);
		double expectedDestinationBalance = accountDestination.getBalance() + (numberOfTransfers * transferAmount);

		// Saldo esperado
		System.out.println("Saldo da Conta de Origem ESPERADO: " + expectedOriginBalance);
		System.out.println("Saldo da Conta de Destino ESPERADO: " + expectedDestinationBalance);

		// Verificar consistência dos saldos
		System.out.println("Saldo da Conta de Origem: " + updatedOrigin.getBalance());
		System.out.println("Saldo da Conta de Destino: " + updatedDestination.getBalance());

		assertThat(updatedOrigin.getBalance()).isEqualTo(expectedOriginBalance);
		assertThat(updatedDestination.getBalance()).isEqualTo(expectedDestinationBalance);
	}
}
