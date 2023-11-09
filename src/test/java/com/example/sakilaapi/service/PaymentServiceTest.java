package com.example.sakilaapi.service;

import com.example.sakilaapi.dto.*;
import com.example.sakilaapi.model.Customer;
import com.example.sakilaapi.model.Payment;
import com.example.sakilaapi.model.Rental;
import com.example.sakilaapi.model.Staff;
import com.example.sakilaapi.repository.CustomerRepository;
import com.example.sakilaapi.repository.PaymentRepository;
import com.example.sakilaapi.repository.RentalRepository;
import com.example.sakilaapi.repository.StaffRepository;
import com.example.sakilaapi.service.impl.PaymentServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    private static final Long PAYMENT_ID = 1L;
    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private RentalRepository rentalRepository;
    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private StaffRepository staffRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private Payment payment;
    private PaymentDto paymentDto;
    private Staff staff;
    private Customer customer;
    private Rental rental;


    @BeforeEach
    public void setup() {
        ModelMapper modelMapper = new ModelMapper();
        paymentService = new PaymentServiceImpl(modelMapper, paymentRepository, rentalRepository, staffRepository, customerRepository);
        rental = Rental.builder().rental_id(1L).build();
        staff = Staff.builder().staff_id(1L).build();
        customer = Customer.builder().customer_id(1L).build();
        payment = Payment.builder().payment_id(1L).customer(customer).staff(staff).rental(rental).build();
        paymentDto = PaymentDto.builder().payment_id(1L).customer(modelMapper.map(customer, CustomerDto.class)).staff(modelMapper.map(staff, StaffDto.class)).rental(modelMapper.map(rental, RentalDto.class)).build();
    }

    @Test
    public void PaymentService_CreatePayment_ReturnPaymentDto() {

        when(paymentRepository.save(Mockito.any(Payment.class))).thenReturn(payment);
        when(staffRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(staff));
        when(rentalRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(rental));
        when(customerRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(customer));

        PaymentDto response = paymentService.createPayment(paymentDto);

        Assertions.assertThat(response).isNotNull();
    }


    @Test
    public void PaymentService_GetAllPayments_ReturnPaymentDtos() {

        Page<Payment> payments = Mockito.mock(Page.class);

        when(paymentRepository.findAll(Mockito.any(Pageable.class))).thenReturn(payments);

        ApiResponse<PaymentDto> response = paymentService.getAllPayments(0, 10);

        Assertions.assertThat(response).isNotNull();
    }

    @Test
    public void PaymentService_GetPaymentById_ReturnPaymentDto() {

        when(paymentRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(payment));

        PaymentDto foundedPayment = paymentService.getPaymentById(PAYMENT_ID);

        Assertions.assertThat(foundedPayment).isNotNull();
        Assertions.assertThat(foundedPayment.getPayment_id()).isEqualTo(PAYMENT_ID);
    }

    @Test
    public void PaymentService_UpdatePayment_ReturnPaymentDto() {

        when(paymentRepository.save(Mockito.any(Payment.class))).thenReturn(payment);
        when(paymentRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(payment));
        when(staffRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(staff));
        when(rentalRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(rental));
        when(customerRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(customer));

        PaymentDto response = paymentService.updatePayment(PAYMENT_ID, paymentDto);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getCustomer().getCustomer_id()).isEqualTo(paymentDto.getCustomer().getCustomer_id());
        Assertions.assertThat(response.getRental().getRental_id()).isEqualTo(paymentDto.getRental().getRental_id());
        Assertions.assertThat(response.getStaff().getStaff_id()).isEqualTo(paymentDto.getStaff().getStaff_id());
        Assertions.assertThat(response.getAmount()).isEqualTo(paymentDto.getAmount());
    }

    @Test
    public void PaymentService_DeletePayment_ReturnPaymentDto() {

        when(paymentRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(payment));

        assertAll(() -> paymentService.deletePayment(PAYMENT_ID));
    }

}