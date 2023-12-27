package com.example.demo;

import com.example.demo.controller.OrderController;
import com.example.demo.dto.requests.PerformPaymentRequest;
import com.example.demo.dto.responses.CheckSmsResponse;
import com.example.demo.dto.responses.CheckSumResponse;
import com.example.demo.dto.responses.PerformPaymentResponse;
import com.example.demo.service.impl.OrderServiceImpl;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class OrderControllerTest {

  @Mock
  private OrderServiceImpl orderServiceImpl;

  @InjectMocks
  private OrderController orderController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void testCheckSum() {
    int id = 1;
    int sum = 120;
    CheckSumResponse expectedResponse = new CheckSumResponse();
    expectedResponse.setResult(true);

    when(orderServiceImpl.checkSum(1L, 120)).thenReturn(ResponseEntity.ok(expectedResponse));

    ResponseEntity<CheckSumResponse> response = orderController.checkSum(id, sum);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(Objects.requireNonNull(response.getBody()).isResult());
    verify(orderServiceImpl, times(1)).checkSum(1L, 120);
  }

  @Test
  void testCheckSms() {
    int id = 1;
    String phone = "1234567890";
    String sms = "1234";
    CheckSmsResponse expectedResponse = new CheckSmsResponse();
    expectedResponse.setResult(true);

    when(orderServiceImpl.checkSms(1, "1234567890", "1234")).thenReturn(ResponseEntity.ok(expectedResponse));

    ResponseEntity<CheckSmsResponse> response = orderController.checkSms(id, phone, sms);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(Objects.requireNonNull(response.getBody()).isResult());
    verify(orderServiceImpl, times(1)).checkSms(1, "1234567890", "1234");
  }

  @Test
  void testPerformPayment() {
    PerformPaymentRequest request = new PerformPaymentRequest(1L, "1234567890123456", "12/25", "123", 100.0, "Address");
    PerformPaymentResponse expectedResponse = new PerformPaymentResponse();
    expectedResponse.setResult(true);

    when(orderServiceImpl.performPayment(1L, "1234567890123456", "12/25", "123", 100.0, "Address"))
        .thenReturn(ResponseEntity.ok(expectedResponse));

    ResponseEntity<PerformPaymentResponse> response = orderController.performPayment(request);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(Objects.requireNonNull(response.getBody()).isResult());
    verify(orderServiceImpl, times(1)).performPayment(1L, "1234567890123456", "12/25", "123", 100.0, "Address");
  }
}
