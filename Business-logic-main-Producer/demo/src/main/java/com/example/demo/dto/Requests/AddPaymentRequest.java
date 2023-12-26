package com.example.demo.dto.Requests;

import lombok.Data;

@Data
public class AddPaymentRequest {
    private long userId;
    private String cardNum;
    private String cardDate;
    private String cardCVV;
}
