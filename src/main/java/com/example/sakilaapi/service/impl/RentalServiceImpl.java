package com.example.sakilaapi.service.impl;

import com.example.sakilaapi.dto.ApiResponse;
import com.example.sakilaapi.dto.RentalDto;
import com.example.sakilaapi.model.Customer;
import com.example.sakilaapi.model.Inventory;
import com.example.sakilaapi.model.Rental;
import com.example.sakilaapi.model.Staff;
import com.example.sakilaapi.repository.CustomerRepository;
import com.example.sakilaapi.repository.InventoryRepository;
import com.example.sakilaapi.repository.RentalRepository;
import com.example.sakilaapi.repository.StaffRepository;
import com.example.sakilaapi.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.sakilaapi.utils.HelperFunctions.getApiResponse;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {

    private final ModelMapper modelMapper;
    private final RentalRepository rentalRepository;
    private final InventoryRepository inventoryRepository;
    private final StaffRepository staffRepository;
    private final CustomerRepository customerRepository;

    @Override
    public RentalDto createRental(RentalDto rentalDto) {
        Inventory inventory = inventoryRepository.findById(rentalDto.getInventory().getInventory_id()).orElseThrow(() -> new RuntimeException("Not Founded Inventory with id: " + rentalDto.getInventory().getInventory_id()));
        Staff staff = staffRepository.findById(rentalDto.getStaff().getStaff_id()).orElseThrow(() -> new RuntimeException("Not Founded Staff with id: " + rentalDto.getStaff().getStaff_id()));
        Customer customer = customerRepository.findById(rentalDto.getCustomer().getCustomer_id()).orElseThrow(() -> new RuntimeException("Not Founded Customer with id: " + rentalDto.getCustomer().getCustomer_id()));
        Rental rental = modelMapper.map(rentalDto, Rental.class);
        rental.setInventory(inventory);
        rental.setStaff(staff);
        rental.setCustomer(customer);
        Rental savedRental = rentalRepository.save(rental);
        return modelMapper.map(savedRental, RentalDto.class);
    }

    @Override
    public ApiResponse<RentalDto> getAllRentals(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Rental> rentals = rentalRepository.findAll(pageable);
        List<Rental> listOfRentals = rentals.getContent();
        List<RentalDto> content = listOfRentals.stream().map(c -> modelMapper.map(c, RentalDto.class)).toList();
        return getApiResponse(pageNo, pageSize, content, rentals);
    }

    @Override
    public RentalDto getRentalById(Long id) {
        Rental rental = rentalRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Founded Rental with id: " + id));
        return modelMapper.map(rental, RentalDto.class);
    }

    @Override
    public RentalDto updateRental(Long id, RentalDto rentalDto) {
        Rental foundedRental = rentalRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Founded Rental with id: " + id));
        Inventory inventory = inventoryRepository.findById(rentalDto.getInventory().getInventory_id()).orElseThrow(() -> new RuntimeException("Not Founded Inventory with id: " + rentalDto.getInventory().getInventory_id()));
        Staff staff = staffRepository.findById(rentalDto.getStaff().getStaff_id()).orElseThrow(() -> new RuntimeException("Not Founded Staff with id: " + rentalDto.getStaff().getStaff_id()));
        Customer customer = customerRepository.findById(rentalDto.getCustomer().getCustomer_id()).orElseThrow(() -> new RuntimeException("Not Founded Customer with id: " + rentalDto.getCustomer().getCustomer_id()));

        foundedRental.setStaff(staff);
        foundedRental.setInventory(inventory);
        foundedRental.setCustomer(customer);
        Rental updatedRental = rentalRepository.save(foundedRental);
        return modelMapper.map(updatedRental, RentalDto.class);

    }

    @Override
    public void deleteRental(Long id) {
        Rental foundedRental = rentalRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Founded Rental with id: " + id));
        rentalRepository.delete(foundedRental);

    }
}
