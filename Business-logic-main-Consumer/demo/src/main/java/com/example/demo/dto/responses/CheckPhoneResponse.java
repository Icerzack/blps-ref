package com.example.demo.dto.responses;

import lombok.Data;

@Data
public class CheckPhoneResponse {
    private String phone;
    private boolean result;
}
