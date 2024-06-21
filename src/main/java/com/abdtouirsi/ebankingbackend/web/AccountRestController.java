package com.abdtouirsi.ebankingbackend.web;

import com.abdtouirsi.ebankingbackend.dtos.*;
import com.abdtouirsi.ebankingbackend.entities.BankAccount;
import com.abdtouirsi.ebankingbackend.exceptions.BalanceNotSufficentException;
import com.abdtouirsi.ebankingbackend.exceptions.BankAccountNotFoundException;
import com.abdtouirsi.ebankingbackend.exceptions.CostumerNotFoundException;
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
    @PostMapping("/bank-accounts/debit")
    private DebitDto debit(@RequestBody DebitDto debitDto) throws BankAccountNotFoundException, BalanceNotSufficentException {
        this.bankAccountService.debit(debitDto.getAccountID(),debitDto.getAmount(),debitDto.getDescription());
        return debitDto;
    }
    @PostMapping("/bank-accounts/credit")
    private CreditDto credit(@RequestBody CreditDto creditDto) throws BankAccountNotFoundException {
        this.bankAccountService.credit(creditDto.getAccountID(),creditDto.getAmount(),creditDto.getDescription());
        return creditDto;
    }
    @PostMapping("/bank-accounts/transfer")
    private void transfer(@RequestBody TransferRequestDto transferDto) throws BankAccountNotFoundException, BalanceNotSufficentException {
        this.bankAccountService.transfer(transferDto.getSourceAccountId(),
                transferDto.getTargetAccountId(),
                transferDto.getAmount());

    }
    //account by customer
    @GetMapping("/bank-accounts/customer/{customerId}")
    public List<BankAccountDto> getBankAccountsByCustomer(@PathVariable Long customerId) {
        return bankAccountService.getBankAccountsByCustomer(customerId);
    }
}
