package com.abdtouirsi.ebankingbackend.mappers;

import com.abdtouirsi.ebankingbackend.dtos.AccountOperationDto;
import com.abdtouirsi.ebankingbackend.dtos.CurrentBankAccountDto;
import com.abdtouirsi.ebankingbackend.dtos.CustomerDto;
import com.abdtouirsi.ebankingbackend.dtos.SavingBankAccountDto;
import com.abdtouirsi.ebankingbackend.entities.AccountOperation;
import com.abdtouirsi.ebankingbackend.entities.CurrentAccount;
import com.abdtouirsi.ebankingbackend.entities.Customer;
import com.abdtouirsi.ebankingbackend.entities.SavingAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BankAccountMapperImpl {
    public CustomerDto fromCustomer(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        BeanUtils.copyProperties(customer, customerDto);
        return customerDto;
    }
    public Customer fromCustomerDto(CustomerDto customerDto) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDto, customer);
        return customer;
    }
    public SavingBankAccountDto fromSavingAccount(SavingAccount savingAccount) {
        SavingBankAccountDto savingBankAccountDto = new SavingBankAccountDto();
        BeanUtils.copyProperties(savingAccount, savingBankAccountDto);
        savingBankAccountDto.setCustomerDto(fromCustomer(savingAccount.getCustomer()));
        savingBankAccountDto.setType(SavingAccount.class.getSimpleName());
        return savingBankAccountDto;
    }
    public SavingAccount fromSavingAccountDto(SavingBankAccountDto savingBankAccountDto) {
        SavingAccount savingAccount = new SavingAccount();
        BeanUtils.copyProperties(savingBankAccountDto, savingAccount);
        savingAccount.setCustomer(fromCustomerDto(savingBankAccountDto.getCustomerDto()));
        return savingAccount;
    }
    public CurrentBankAccountDto fromCurrentAccount(CurrentAccount currentAccount) {
        CurrentBankAccountDto currentBankAccountDto = new CurrentBankAccountDto();
        BeanUtils.copyProperties(currentAccount, currentBankAccountDto);
        currentBankAccountDto.setCustomerDto(fromCustomer(currentAccount.getCustomer()));
        currentBankAccountDto.setType(CurrentAccount.class.getSimpleName());
        return currentBankAccountDto;
    }
    public CurrentAccount fromCurrentAccountDto(CurrentBankAccountDto currentBankAccountDto) {
        CurrentAccount currentAccount = new CurrentAccount();
        BeanUtils.copyProperties(currentBankAccountDto, currentAccount);
        currentAccount.setCustomer(fromCustomerDto(currentBankAccountDto.getCustomerDto()));
        return currentAccount;
    }

    public AccountOperationDto fromAccountOperation(AccountOperation accountOperation) {
        AccountOperationDto accountOperationDto = new AccountOperationDto();
        BeanUtils.copyProperties(accountOperation, accountOperationDto);
        return accountOperationDto;
    }
    public AccountOperation fromAccountOperationDto(AccountOperationDto accountOperationDto) {
        AccountOperation accountOperation = new AccountOperation();
        BeanUtils.copyProperties(accountOperationDto, accountOperation);
        return accountOperation;
    }
}
