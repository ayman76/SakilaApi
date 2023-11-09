package com.example.sakilaapi.service;

import com.example.sakilaapi.dto.ApiResponse;
import com.example.sakilaapi.dto.PaymentDto;

public interface PaymentService {
    PaymentDto createPayment(PaymentDto paymentDto);

    ApiResponse<PaymentDto> getAllPayments(int pageNo, int pageSize);

    PaymentDto getPaymentById(Long id);

    PaymentDto updatePayment(Long id, PaymentDto paymentDto);

    void deletePayment(Long id);
}
