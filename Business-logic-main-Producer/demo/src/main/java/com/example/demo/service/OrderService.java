package com.example.demo.service;

import com.example.demo.dto.Responses.CheckSmsResponse;
import com.example.demo.dto.Responses.CheckSumResponse;
import com.example.demo.dto.Responses.PerformPaymentResponse;
import org.springframework.http.ResponseEntity;

public interface OrderService {
    ResponseEntity<CheckSumResponse> checkSum(Long orderId, double sumThreshold);

    ResponseEntity<CheckSmsResponse> checkSms(Integer orderId, String phoneNumber, String sms);

    ResponseEntity<PerformPaymentResponse> performPayment(long userId, String cardNum, String cardDate, String cardCVV, Double cost, String address);
}
