package com.abdtouirsi.ebankingbackend.web;

import com.abdtouirsi.ebankingbackend.dtos.AccountHistoryDto;
import com.abdtouirsi.ebankingbackend.dtos.AccountOperationDto;
import com.abdtouirsi.ebankingbackend.dtos.BankAccountDto;
import com.abdtouirsi.ebankingbackend.exceptions.BankAccountNotFoundException;
import com.abdtouirsi.ebankingbackend.services.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController @AllArgsConstructor
public class AccountRestController {
    BankAccountService bankAccountService;
    @GetMapping("/bank-accounts/{id}")
    public BankAccountDto getBankAccountById(@PathVariable String id) throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(id);
    }

    @GetMapping("/bank-accounts")
    public List<BankAccountDto> getAllBankAccounts() {
        return bankAccountService.listBankAccounts();
    }

    @GetMapping("/bank-accounts/{id}/history")
    public List<AccountOperationDto> getHistory(@PathVariable String id) {
        return bankAccountService.accountHistory(id);
    }
    @GetMapping("/bank-accounts/{id}/account-history")
    public AccountHistoryDto getAccountHistory(@PathVariable String id,
                                               @RequestParam(name = "page",defaultValue = "0") int page,
                                               @RequestParam(name = "size",defaultValue = "10") int size) throws BankAccountNotFoundException {
        return bankAccountService.getAccountHistory(id,page,size);
    }
}
