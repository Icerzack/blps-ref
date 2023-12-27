package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

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
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootTest
public class OrderServiceTests {

  @Mock
  private KafkaTemplate<String, PerformPaymentRequest> kafkaTemplate;

  @InjectMocks
  private OrderServiceImpl orderService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void testCheckSum() {
    ResponseEntity<CheckSumResponse> response = orderService.checkSum(123L, 150.0);
    assertTrue(Objects.requireNonNull(response.getBody()).isResult());
  }

  @Test
  void testCheckSms() {
    ResponseEntity<CheckSmsResponse> response = orderService.checkSms(123, "1234567890", "1234");
    assertFalse(Objects.requireNonNull(response.getBody()).isResult());
  }

  @Test
  void testPerformPaymentValid() {
    ResponseEntity<PerformPaymentResponse> response = orderService.performPayment(1L, "1234567890123456",
        "12/25", "123", 500.0, "123 Street");
    assertTrue(Objects.requireNonNull(response.getBody()).isResult());
    verify(kafkaTemplate, times(1)).send(eq("perform-test3"), any(PerformPaymentRequest.class));
  }

  @Test
  void testPerformPaymentInvalid() {
    ResponseEntity<PerformPaymentResponse> response = orderService.performPayment(1L, "123",
        "12/25", "123", 500.0, "123 Street");
    assertFalse(Objects.requireNonNull(response.getBody()).isResult());
    verifyNoInteractions(kafkaTemplate);
  }

  @Test
  void testPerformPaymentWithInvalidCardCVV() {
    ResponseEntity<PerformPaymentResponse> response = orderService.performPayment(
        1L, "1234567890123456", "12/25", "12", 500.0, "123 Street");

    assertFalse(Objects.requireNonNull(response.getBody()).isResult());
    verifyNoInteractions(kafkaTemplate);
  }

  @Test
  void testPerformPaymentWithInvalidCardNum() {
    ResponseEntity<PerformPaymentResponse> response = orderService.performPayment(
        1L, "123", "12/25", "123", 500.0, "123 Street");

    assertFalse(Objects.requireNonNull(response.getBody()).isResult());
    verifyNoInteractions(kafkaTemplate);
  }

  @Test
  void testPerformPaymentWithInvalidCost() {
    ResponseEntity<PerformPaymentResponse> response = orderService.performPayment(
        1L, "1234567890123456", "12/25", "123", -50.0, "123 Street");

    assertFalse(Objects.requireNonNull(response.getBody()).isResult());
    verifyNoInteractions(kafkaTemplate);
  }

  @Test
  void testPerformPaymentWithValidPaymentDetails() {
    ResponseEntity<PerformPaymentResponse> response = orderService.performPayment(
        1L, "1234567890123456", "12/25", "123", 500.0, "123 Street");

    assertTrue(Objects.requireNonNull(response.getBody()).isResult());
    verify(kafkaTemplate, times(1)).send(eq("perform-test3"), any(PerformPaymentRequest.class));
  }

}
