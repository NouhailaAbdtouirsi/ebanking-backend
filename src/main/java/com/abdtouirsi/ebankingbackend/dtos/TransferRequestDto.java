package com.abdtouirsi.ebankingbackend.dtos;

import lombok.Data;

@Data
public class TransferRequestDto {
    private String sourceAccountId;
    private String targetAccountId;
    private double amount;
    private String description;
}
