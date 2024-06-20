package com.abdtouirsi.ebankingbackend.repositories;

import com.abdtouirsi.ebankingbackend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CostumerRepository extends JpaRepository<Customer, Long> {
    @Query("select c from Customer c where c.name like %:keyword% or c.email like %:keyword%")
    List<Customer> searchCustomer(@Param("keyword") String keyword);
}
