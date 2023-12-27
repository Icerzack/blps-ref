package com.example.demo.dto.requests;

import lombok.Data;

@Data
public class AddPhoneRequest {
    private long userId;
    private String phoneNumber;
}
