package com.example.sakilaapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalDto {
    private Long rental_id;
    private InventoryDto inventory;
    private CustomerDto customer;
    private StaffDto staff;
}
