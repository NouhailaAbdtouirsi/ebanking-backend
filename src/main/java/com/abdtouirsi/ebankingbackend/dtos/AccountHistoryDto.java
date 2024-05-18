package com.abdtouirsi.ebankingbackend.dtos;

import lombok.Data;

import java.util.List;
@Data
public class AccountHistoryDto {
    private String accountId;
    private double balance;
    private int currentPage;
    private int totalPages;
    private int size;
    private List<AccountOperationDto> operations;
}
