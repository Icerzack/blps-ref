package com.example.demo.dto.Responses;

import lombok.Data;

@Data
public class CheckPaymentResponse {
    private boolean result;
    private String cardNumber;
    private String cardDate;
    private String cardCVV;
}
