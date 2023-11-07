package com.example.sakilaapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

    private Long address_id;
    private String address;
    private String address2;
    private String district;
    private CityDto city;
    private String postal_code;
    private String phone;
}
