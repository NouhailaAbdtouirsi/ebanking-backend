package com.abdtouirsi.ebankingbackend;

import com.abdtouirsi.ebankingbackend.dtos.BankAccountDto;
import com.abdtouirsi.ebankingbackend.dtos.CurrentBankAccountDto;
import com.abdtouirsi.ebankingbackend.dtos.CustomerDto;
import com.abdtouirsi.ebankingbackend.dtos.SavingBankAccountDto;
import com.abdtouirsi.ebankingbackend.enums.AccountStatus;
import com.abdtouirsi.ebankingbackend.enums.OperationType;
import com.abdtouirsi.ebankingbackend.exceptions.BalanceNotSufficentException;
import com.abdtouirsi.ebankingbackend.exceptions.BankAccountNotFoundException;
import com.abdtouirsi.ebankingbackend.exceptions.CostumerNotFoundException;
import com.abdtouirsi.ebankingbackend.repositories.AccountOperationRepository;
import com.abdtouirsi.ebankingbackend.repositories.BankAccountRepository;
import com.abdtouirsi.ebankingbackend.repositories.CostumerRepository;
import com.abdtouirsi.ebankingbackend.services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.abdtouirsi.ebankingbackend.entities.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankingBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankingBackendApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService){
        return args -> {
            Stream.of("Nouaila","Med","Leila").forEach(name->{
                        CustomerDto customer = new CustomerDto();
                        customer.setName(name);
                        customer.setEmail(name+"@outlook.fr");
                        bankAccountService.saveCustomer(customer);
                    });
            bankAccountService.listCustomer().forEach(customer -> {
                try {
                    bankAccountService.saveCurrentBankAccount(Math.random()*90000,9000,customer.getId());
                    bankAccountService.saveSavingBankAccount(Math.random()*190000,4.5,customer.getId());
                    List<BankAccountDto> bankAccountList= bankAccountService.listBankAccounts();
                    for(BankAccountDto bankAccount:bankAccountList){
                        String id ;
                        if(bankAccount instanceof CurrentBankAccountDto){
                            id=((CurrentBankAccountDto) bankAccount).getId();
                        }else{
                            id=((SavingBankAccountDto) bankAccount).getId();
                        }
                        bankAccountService.credit(id,10000+ Math.random()*120000,"Credit");
                        bankAccountService.debit(id,1000+Math.random()*9000,"Debit");
                    }

                } catch (CostumerNotFoundException | BankAccountNotFoundException | BalanceNotSufficentException e) {
                    e.printStackTrace();
                }
            });

        };
    }
    //@Bean
    CommandLineRunner start(CostumerRepository costumerRepository,
                            BankAccountRepository bankAccountRepository,
                            AccountOperationRepository accountOperationRepository){
        return args -> {
            Stream.of("Nouhaila", "Youssef", "Leila").forEach(name -> {
                Customer costumer = new Customer();
                costumer.setName(name);
                costumer.setEmail(name+"@gmail.com");
                costumerRepository.save(costumer);
            });
            costumerRepository.findAll().forEach(customer -> {
                        CurrentAccount currentAccount = new CurrentAccount();
                        currentAccount.setId(UUID.randomUUID().toString());
                        currentAccount.setBalance(90000*Math.random());
                        currentAccount.setCreationDate(new Date());
                        currentAccount.setStatus(AccountStatus.CREATED);
                        currentAccount.setCustomer(customer);
                        currentAccount.setOverdraft(9000);
                        bankAccountRepository.save(currentAccount);

                        SavingAccount savingsAccount = new SavingAccount();
                        savingsAccount.setId(UUID.randomUUID().toString());
                        savingsAccount.setBalance(90000*Math.random());
                        savingsAccount.setCreationDate(new Date());
                        savingsAccount.setStatus(AccountStatus.CREATED);
                        savingsAccount.setCustomer(customer);
                        savingsAccount.setInterestRate(3.5);
                        bankAccountRepository.save(savingsAccount);

                        }
                    );
            bankAccountRepository.findAll().forEach(bankAccount -> {
                for (int i = 0; i < 5; i++) {
                    AccountOperation accountOperation = new AccountOperation();
                    accountOperation.setAmount(1000*Math.random());
                    accountOperation.setOperationDate(new Date());
                    accountOperation.setBankAccount(bankAccount);
                    accountOperation.setOperationType(i%2==0? OperationType.CREDIT:OperationType.DEBIT);
                    accountOperationRepository.save(accountOperation);
                }
            });
        };
    }

}
