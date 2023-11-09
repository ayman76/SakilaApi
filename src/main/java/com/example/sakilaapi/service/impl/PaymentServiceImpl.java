package com.example.sakilaapi.service.impl;

import com.example.sakilaapi.dto.ApiResponse;
import com.example.sakilaapi.dto.PaymentDto;
import com.example.sakilaapi.model.Customer;
import com.example.sakilaapi.model.Payment;
import com.example.sakilaapi.model.Rental;
import com.example.sakilaapi.model.Staff;
import com.example.sakilaapi.repository.CustomerRepository;
import com.example.sakilaapi.repository.PaymentRepository;
import com.example.sakilaapi.repository.RentalRepository;
import com.example.sakilaapi.repository.StaffRepository;
import com.example.sakilaapi.service.PaymentService;
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
public class PaymentServiceImpl implements PaymentService {

    private final ModelMapper modelMapper;
    private final PaymentRepository paymentRepository;
    private final RentalRepository rentalRepository;
    private final StaffRepository staffRepository;
    private final CustomerRepository customerRepository;

    @Override
    public PaymentDto createPayment(PaymentDto paymentDto) {
        Rental rental = rentalRepository.findById(paymentDto.getRental().getRental_id()).orElseThrow(() -> new RuntimeException("Not Founded Rental with id: " + paymentDto.getRental().getRental_id()));
        Staff staff = staffRepository.findById(paymentDto.getStaff().getStaff_id()).orElseThrow(() -> new RuntimeException("Not Founded Staff with id: " + paymentDto.getStaff().getStaff_id()));
        Customer customer = customerRepository.findById(paymentDto.getCustomer().getCustomer_id()).orElseThrow(() -> new RuntimeException("Not Founded Customer with id: " + paymentDto.getCustomer().getCustomer_id()));
        Payment payment = modelMapper.map(paymentDto, Payment.class);
        payment.setRental(rental);
        payment.setStaff(staff);
        payment.setCustomer(customer);
        Payment savedPayment = paymentRepository.save(payment);
        return modelMapper.map(savedPayment, PaymentDto.class);
    }

    @Override
    public ApiResponse<PaymentDto> getAllPayments(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Payment> payments = paymentRepository.findAll(pageable);
        List<Payment> listOfPayments = payments.getContent();
        List<PaymentDto> content = listOfPayments.stream().map(c -> modelMapper.map(c, PaymentDto.class)).toList();
        return getApiResponse(pageNo, pageSize, content, payments);
    }

    @Override
    public PaymentDto getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Founded Payment with id: " + id));
        return modelMapper.map(payment, PaymentDto.class);
    }

    @Override
    public PaymentDto updatePayment(Long id, PaymentDto paymentDto) {
        Payment foundedPayment = paymentRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Founded Payment with id: " + id));
        Rental rental = rentalRepository.findById(paymentDto.getRental().getRental_id()).orElseThrow(() -> new RuntimeException("Not Founded Rental with id: " + paymentDto.getRental().getRental_id()));
        Staff staff = staffRepository.findById(paymentDto.getStaff().getStaff_id()).orElseThrow(() -> new RuntimeException("Not Founded Staff with id: " + paymentDto.getStaff().getStaff_id()));
        Customer customer = customerRepository.findById(paymentDto.getCustomer().getCustomer_id()).orElseThrow(() -> new RuntimeException("Not Founded Customer with id: " + paymentDto.getCustomer().getCustomer_id()));

        foundedPayment.setStaff(staff);
        foundedPayment.setRental(rental);
        foundedPayment.setCustomer(customer);
        foundedPayment.setAmount(paymentDto.getAmount());
        Payment updatedPayment = paymentRepository.save(foundedPayment);
        return modelMapper.map(updatedPayment, PaymentDto.class);

    }

    @Override
    public void deletePayment(Long id) {
        Payment foundedPayment = paymentRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Founded Payment with id: " + id));
        paymentRepository.delete(foundedPayment);

    }
}
