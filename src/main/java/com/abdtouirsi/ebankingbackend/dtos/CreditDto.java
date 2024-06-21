package com.abdtouirsi.ebankingbackend.dtos;

import lombok.Data;

@Data
public class CreditDto {
    private String accountID;
    private double amount;
    private String description;
}
