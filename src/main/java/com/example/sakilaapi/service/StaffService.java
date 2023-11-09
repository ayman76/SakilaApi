package com.example.sakilaapi.service;

import com.example.sakilaapi.dto.ApiResponse;
import com.example.sakilaapi.dto.StaffDto;
import com.example.sakilaapi.dto.StaffRequestDto;

public interface StaffService {
    StaffDto createStaff(StaffRequestDto staffDto);

    ApiResponse<StaffDto> getAllStaffs(int pageNo, int pageSize);

    StaffDto getStaffById(Long id);

    StaffDto getStaffByName(String firstName);

    StaffDto updateStaff(Long id, StaffRequestDto staffDto);

    void deleteStaff(Long id);
}
