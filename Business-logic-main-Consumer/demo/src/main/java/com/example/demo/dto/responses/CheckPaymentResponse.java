package com.example.demo.dto.responses;

import lombok.Data;

@Data
public class CheckPaymentResponse {
    private boolean result;
    private String cardNumber;
    private String cardDate;
    private String cardCVV;
}
