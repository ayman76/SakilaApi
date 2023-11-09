package com.example.sakilaapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffRequestDto {
    private Long staff_id;
    private String first_name;
    private String last_name;
    private String email;
    private String username;
    private String password;
    private boolean active;
    private AddressDto address;
    private StoreDto store;
}
