package com.example.sakilaapi.repository;

import com.example.sakilaapi.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
    @Query("select s from Staff as s where s.first_name = :firstName")
    Optional<Staff> findStaffByFirstName(String firstName);
}
