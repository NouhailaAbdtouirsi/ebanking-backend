package com.abdtouirsi.ebankingbackend.web;

import com.abdtouirsi.ebankingbackend.dtos.CustomerDto;
import com.abdtouirsi.ebankingbackend.entities.Customer;
import com.abdtouirsi.ebankingbackend.exceptions.CostumerNotFoundException;
import com.abdtouirsi.ebankingbackend.services.BankAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
@Slf4j

public class CustomerRestController {
    private BankAccountService bankAccountService;
    @GetMapping("/customers")
    @PreAuthorize("hasAuthority('SCOPE_USER') or hasAuthority('SCOPE_ADMIN')")
    public List<CustomerDto> listCustomers(){
        return bankAccountService.listCustomer();
    }
    @GetMapping("/searchCustomers")
    //scope user and admin can access this endpoint
    @PreAuthorize("hasAuthority('SCOPE_USER') or hasAuthority('SCOPE_ADMIN')")
    public List<CustomerDto> searchCustomers(@RequestParam(name = "keyword",defaultValue = "") String keyword){
        return bankAccountService.searchCustomers(keyword);
    }
    @GetMapping("/customers/{id}")
    @PreAuthorize("hasAuthority('SCOPE_USER') or hasAuthority('SCOPE_ADMIN')")
    public CustomerDto getCustomer(@PathVariable Long id) throws CostumerNotFoundException {
        return bankAccountService.getCustomer(id);
    }
    @PostMapping("/customers")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public CustomerDto saveCustomer(@RequestBody CustomerDto customerDto){
         bankAccountService.saveCustomer(customerDto);
         return customerDto;
    }
    @PutMapping("/customers/{costumerId}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public CustomerDto updateCustomer(@PathVariable Long costumerId,@RequestBody CustomerDto customerDto){
        customerDto.setId(costumerId);
        return bankAccountService.updateCustomer(customerDto);
    }
    @DeleteMapping("/customers/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void deleteCustomer(@PathVariable Long id) throws CostumerNotFoundException {
        bankAccountService.deleteCustomer(id);
    }
}
