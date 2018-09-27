package com.ws.patient.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ws.patient.model.Account;



public interface AccountDao extends JpaRepository<Account, Long> {

	Optional<Account> findByUsername(final String username);
}
