package com.example.demo.service;

import com.example.demo.dto.responses.*;
import org.springframework.http.ResponseEntity;

public interface UserService {

    ResponseEntity<AddPhoneResponse> addPhone(Long id, String phone);

    ResponseEntity<CheckPhoneResponse> checkPhone(Long id);

    ResponseEntity<DeletePhoneResponse> deletePhone(Long id);

    ResponseEntity<AddPaymentResponse> addPayment(Long userId, String cardNum, String cardDate, String cardCVV);

    ResponseEntity<CheckPaymentResponse> checkPayment(Long id);

    ResponseEntity<DeletePaymentResponse> deletePayment(Long id);
}
