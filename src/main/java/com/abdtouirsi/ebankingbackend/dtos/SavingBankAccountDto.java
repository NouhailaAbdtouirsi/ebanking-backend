package com.abdtouirsi.ebankingbackend.dtos;
import com.abdtouirsi.ebankingbackend.enums.AccountStatus;

import lombok.Data;

import java.util.Date;

@Data
public class SavingBankAccountDto extends BankAccountDto{
    private String id;
    private double balance;
    private Date creationDate;
    private AccountStatus status;
    private CustomerDto customerDto;
    private double interestRate;
}
