package com.example.demo.dto.Requests;

import lombok.Data;

@Data
public class AddPhoneRequest {
    private long userId;
    private String phoneNumber;
}
