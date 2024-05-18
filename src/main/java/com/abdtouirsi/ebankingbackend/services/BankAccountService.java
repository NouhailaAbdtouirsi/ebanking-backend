package com.abdtouirsi.ebankingbackend.services;

import com.abdtouirsi.ebankingbackend.dtos.*;
import com.abdtouirsi.ebankingbackend.exceptions.BalanceNotSufficentException;
import com.abdtouirsi.ebankingbackend.exceptions.BankAccountNotFoundException;
import com.abdtouirsi.ebankingbackend.exceptions.CostumerNotFoundException;

import java.util.List;

public interface BankAccountService {
    CustomerDto saveCustomer(CustomerDto customer);
    CurrentBankAccountDto saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CostumerNotFoundException;
    SavingBankAccountDto saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CostumerNotFoundException;
    List<CustomerDto> listCustomer();
    BankAccountDto getBankAccount(String accountId) throws BankAccountNotFoundException;
    void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficentException;
    void credit(String accountId, double amount, String description) throws BankAccountNotFoundException;
    void transfer(String fromAccountId, String toAccountId, double amount) throws BankAccountNotFoundException, BalanceNotSufficentException;

    List<BankAccountDto> listBankAccounts();

    CustomerDto getCustomer(Long id) throws CostumerNotFoundException;

    CustomerDto updateCustomer(CustomerDto customerDto);

    void deleteCustomer(Long id) throws CostumerNotFoundException;

    List<AccountOperationDto> accountHistory(String accountId);

    AccountHistoryDto getAccountHistory(String id, int page, int size) throws BankAccountNotFoundException;
}
