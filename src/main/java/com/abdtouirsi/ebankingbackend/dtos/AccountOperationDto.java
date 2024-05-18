package com.abdtouirsi.ebankingbackend.dtos;

import com.abdtouirsi.ebankingbackend.entities.BankAccount;
import com.abdtouirsi.ebankingbackend.enums.OperationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
public class AccountOperationDto {
    private Long id;
    private Date operationDate;
    private double amount;
    private OperationType operationType;
    private String description;

}
