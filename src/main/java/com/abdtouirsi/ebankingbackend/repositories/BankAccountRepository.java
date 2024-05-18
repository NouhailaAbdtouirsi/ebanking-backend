package com.abdtouirsi.ebankingbackend.repositories;

import com.abdtouirsi.ebankingbackend.entities.BankAccount;
import com.abdtouirsi.ebankingbackend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
}
