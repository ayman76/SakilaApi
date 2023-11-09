package com.example.sakilaapi.repository;

import com.example.sakilaapi.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("select c from Customer as c where c.first_name = :firstName")
    Optional<Customer> findByFirstName(String firstName);
}
