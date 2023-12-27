package com.example.demo;

import com.example.demo.controller.UserController;
import com.example.demo.dto.requests.AddPaymentRequest;
import com.example.demo.dto.requests.AddPhoneRequest;
import com.example.demo.dto.responses.*;
import com.example.demo.service.impl.UserServiceImpl;
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
class UserControllerTests {

  @Mock
  private UserServiceImpl userServiceImpl;

  @InjectMocks
  private UserController userController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testAddPhone() {
    AddPhoneRequest request = new AddPhoneRequest();
    request.setUserId(1L);
    request.setPhoneNumber("1234567890");
    AddPhoneResponse expectedResponse = new AddPhoneResponse();
    expectedResponse.setResult(true);

    when(userServiceImpl.addPhone(1L, "1234567890")).thenReturn(ResponseEntity.ok(expectedResponse));

    ResponseEntity<AddPhoneResponse> response = userController.addPhone(request);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(Objects.requireNonNull(response.getBody()).isResult());
    verify(userServiceImpl, times(1)).addPhone(1L, "1234567890");
  }

  @Test
  void testAddPayment() {
    AddPaymentRequest request = new AddPaymentRequest();
    request.setUserId(1L);
    request.setCardNum("1234567890123456");
    request.setCardDate("12/25");
    request.setCardCVV("123");
    AddPaymentResponse expectedResponse = new AddPaymentResponse();
    expectedResponse.setResult(true);

    when(userServiceImpl.addPayment(1L, "1234567890123456", "12/25", "123")).thenReturn(ResponseEntity.ok(expectedResponse));

    ResponseEntity<AddPaymentResponse> response = userController.addPayment(request);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertTrue(Objects.requireNonNull(response.getBody()).isResult());
    verify(userServiceImpl, times(1)).addPayment(1L, "1234567890123456", "12/25", "123");
  }

}
