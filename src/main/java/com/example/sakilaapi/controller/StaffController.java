package com.example.sakilaapi.controller;

import com.example.sakilaapi.dto.ApiResponse;
import com.example.sakilaapi.dto.StaffDto;
import com.example.sakilaapi.dto.StaffRequestDto;
import com.example.sakilaapi.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/staffs")
public class StaffController {
    private final StaffService staffService;

    @PostMapping("/create")
    public ResponseEntity<StaffDto> createStaff(@RequestBody StaffRequestDto staffDto) {
        return new ResponseEntity<>(staffService.createStaff(staffDto), HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<StaffDto>> getAllStaffs(@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                                              @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return new ResponseEntity<>(staffService.getAllStaffs(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("/{identifier}")
    public ResponseEntity<StaffDto> getStaffById(@PathVariable String identifier) {
        if (identifier.matches("\\d+")) {
            return ResponseEntity.ok(staffService.getStaffById(Long.valueOf(identifier)));
        } else {
            return ResponseEntity.ok(staffService.getStaffByName(identifier));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<StaffDto> updateStaff(@PathVariable Long id, @RequestBody StaffRequestDto staffDto) {
        return new ResponseEntity<>(staffService.updateStaff(id, staffDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StaffDto> deleteStaff(@PathVariable Long id) {
        staffService.deleteStaff(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
