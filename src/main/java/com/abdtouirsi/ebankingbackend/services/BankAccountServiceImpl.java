package com.abdtouirsi.ebankingbackend.services;

import com.abdtouirsi.ebankingbackend.dtos.*;
import com.abdtouirsi.ebankingbackend.entities.*;
import com.abdtouirsi.ebankingbackend.enums.OperationType;
import com.abdtouirsi.ebankingbackend.exceptions.BalanceNotSufficentException;
import com.abdtouirsi.ebankingbackend.exceptions.BankAccountNotFoundException;
import com.abdtouirsi.ebankingbackend.exceptions.CostumerNotFoundException;
import com.abdtouirsi.ebankingbackend.mappers.BankAccountMapperImpl;
import com.abdtouirsi.ebankingbackend.repositories.AccountOperationRepository;
import com.abdtouirsi.ebankingbackend.repositories.BankAccountRepository;
import com.abdtouirsi.ebankingbackend.repositories.CostumerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service @Transactional @AllArgsConstructor @Slf4j
public class BankAccountServiceImpl implements BankAccountService {
    CostumerRepository costumerRepository;
    BankAccountRepository bankAccountRepository;
    AccountOperationRepository accountOperationRepository;
    BankAccountMapperImpl bankAccountMapper;
    @Override
    public CustomerDto saveCustomer(CustomerDto customerDto) {
        log.info("Saving customer {}", customerDto);
        Customer customer = bankAccountMapper.fromCustomerDto(customerDto);
        return bankAccountMapper.fromCustomer(costumerRepository.save(customer));
    }

    @Override
    public CurrentBankAccountDto saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CostumerNotFoundException {
        Customer customer = costumerRepository.findById(customerId).orElse(null);
        if(customer==null)
            throw new CostumerNotFoundException("Customer not found");
        CurrentAccount bankAccount = new CurrentAccount();
        bankAccount.setId(UUID.randomUUID().toString());
        bankAccount.setBalance(initialBalance);
        bankAccount.setCreationDate(new Date());
        bankAccount.setOverdraft(overDraft);
        bankAccount.setCustomer(customer);
        return bankAccountMapper.fromCurrentAccount(bankAccountRepository.save(bankAccount));
    }

    @Override
    public SavingBankAccountDto saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CostumerNotFoundException {
        Customer customer = costumerRepository.findById(customerId).orElse(null);
        if(customer==null)
            throw new CostumerNotFoundException("Customer not found");

        SavingAccount bankAccount = new SavingAccount();
        bankAccount.setId(UUID.randomUUID().toString());
        bankAccount.setBalance(initialBalance);
        bankAccount.setCreationDate(new Date());
        bankAccount.setInterestRate(interestRate);
        bankAccount.setCustomer(customer);


        return bankAccountMapper.fromSavingAccount(bankAccountRepository.save(bankAccount));

    }

    @Override
    public List<CustomerDto> listCustomer() {
        List<Customer> customers = costumerRepository.findAll();
        //        List<CustomerDto> customerDtos = new ArrayList<>();
        //for (Customer customer : customers) {
        //customerDtos.add(bankAccountMapper.fromCustomer(customer));
        //  }
        return customers.stream().map(customer -> bankAccountMapper.fromCustomer(customer)).toList();
    }

    @Override
    public BankAccountDto getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElse(null);
        if(bankAccount==null)
            throw new BankAccountNotFoundException("Bank account not found");
        if(bankAccount instanceof CurrentAccount)
            return bankAccountMapper.fromCurrentAccount((CurrentAccount) bankAccount);
        else
            return bankAccountMapper.fromSavingAccount((SavingAccount) bankAccount);
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficentException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElse(null);
        assert bankAccount != null;
        if(bankAccount.getBalance()<amount)
            throw new BalanceNotSufficentException("Balance not sufficient");
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setAmount(amount);
        accountOperation.setOperationType(OperationType.DEBIT);
        accountOperation.setOperationDate(new Date());
        accountOperation.setDescription(description);
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElse(null);
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setAmount(amount);
        accountOperation.setOperationType(OperationType.CREDIT);
        accountOperation.setOperationDate(new Date());
        accountOperation.setDescription(description);
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        assert bankAccount != null;
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepository.save(bankAccount);

    }

    @Override
    public void transfer(String fromAccountId, String toAccountId, double amount) throws BankAccountNotFoundException, BalanceNotSufficentException {
        debit(fromAccountId,amount,"Transfer to "+toAccountId);
        credit(toAccountId,amount,"Transfer from "+fromAccountId);
    }

    @Override
    public List<BankAccountDto> listBankAccounts() {
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
        List<BankAccountDto> bankAccountDtos = new ArrayList<>();
        for (BankAccount bankAccount : bankAccounts) {
            if(bankAccount instanceof CurrentAccount)
                bankAccountDtos.add(bankAccountMapper.fromCurrentAccount((CurrentAccount) bankAccount));
            else
                bankAccountDtos.add(bankAccountMapper.fromSavingAccount((SavingAccount) bankAccount));
        }
        return bankAccountDtos;
    }

    @Override
    public CustomerDto getCustomer(Long id) throws CostumerNotFoundException {
        Customer customer = costumerRepository.findById(id).orElseThrow(() -> new CostumerNotFoundException("Customer not found"));
        return bankAccountMapper.fromCustomer(customer);
    }
    @Override
    public CustomerDto updateCustomer(CustomerDto customerDto) {
        log.info("Saving customer {}", customerDto);
        Customer customer = bankAccountMapper.fromCustomerDto(customerDto);
        return bankAccountMapper.fromCustomer(costumerRepository.save(customer));
    }

    @Override
    public void deleteCustomer(Long id) throws CostumerNotFoundException {
        costumerRepository.deleteById(id);
    }
    @Override
    public List<AccountOperationDto> accountHistory(String accountId) {
        List<AccountOperation> accountOperations = accountOperationRepository.findByBankAccountId(accountId);
        return accountOperations.stream().map(accountOperation -> bankAccountMapper.fromAccountOperation(accountOperation)).collect(Collectors.toList());
    }

    @Override
    public AccountHistoryDto getAccountHistory(String id, int page, int size) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(id).orElse(null);
        if(bankAccount==null)
            throw new BankAccountNotFoundException("Bank account not found");

        Page<AccountOperation> byBankAccountId = accountOperationRepository.findByBankAccountId(id, PageRequest.of(page, size));
        AccountHistoryDto accountHistoryDto = new AccountHistoryDto();
        List<AccountOperationDto> accountOperationDto = byBankAccountId.getContent().stream().map(accountOperation -> bankAccountMapper.fromAccountOperation(accountOperation)).collect(Collectors.toList());
        accountHistoryDto.setOperations(accountOperationDto);
        accountHistoryDto.setAccountId(bankAccount.getId());
        accountHistoryDto.setBalance(bankAccount.getBalance());
        accountHistoryDto.setSize(size);
        accountHistoryDto.setCurrentPage(page);
        accountHistoryDto.setTotalPages(byBankAccountId.getTotalPages());
        return accountHistoryDto;

    }

    @Override
    public List<CustomerDto> searchCustomers(String keyword) {
        List<Customer> customers = costumerRepository.searchCustomer(keyword);
        return customers.stream().map(
                cust -> bankAccountMapper.fromCustomer(cust)
        ).collect(Collectors.toList());
    }


}
