package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entities.Account;

import jakarta.persistence.LockModeType;

public interface AccountRepository extends JpaRepository<Account, Long> {
	 @Lock(LockModeType.PESSIMISTIC_WRITE)
	    @Query("SELECT a FROM Account a WHERE a.id = :id")
	    Account findByIdWithLock(Long id);
}