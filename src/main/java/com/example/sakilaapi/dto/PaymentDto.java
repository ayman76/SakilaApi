package com.example.sakilaapi.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {
    private Long payment_id;
    private Float amount;
    private CustomerDto customer;
    private StaffDto staff;
    private RentalDto rental;
}
