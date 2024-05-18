package com.abdtouirsi.ebankingbackend.dtos;

import com.abdtouirsi.ebankingbackend.enums.AccountStatus;
import lombok.Data;

import java.util.Date;

@Data
public class CurrentBankAccountDto extends BankAccountDto{
    private String id;
    private double balance;
    private Date creationDate;
    private AccountStatus status;
    private CustomerDto customerDto;
    private double overdraftLimit;
}
