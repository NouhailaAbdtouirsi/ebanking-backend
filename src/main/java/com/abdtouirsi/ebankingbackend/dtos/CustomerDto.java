package com.abdtouirsi.ebankingbackend.dtos;

import com.abdtouirsi.ebankingbackend.entities.BankAccount;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
public class CustomerDto {
    private Long id;
    private String name;
    private String email;
}
