package com.example.demo.dto.requests;

import lombok.Data;

@Data
public class AddPaymentRequest {
    private long userId;
    private String cardNum;
    private String cardDate;
    private String cardCVV;
}
