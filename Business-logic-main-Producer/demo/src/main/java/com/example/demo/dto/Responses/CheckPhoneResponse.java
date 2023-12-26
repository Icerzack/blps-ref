package com.example.demo.dto.Responses;

import lombok.Data;

@Data
public class CheckPhoneResponse {
    private String phone;
    private boolean result;
}
