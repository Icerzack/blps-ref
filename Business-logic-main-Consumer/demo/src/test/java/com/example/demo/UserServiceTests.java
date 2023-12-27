package com.example.demo;

import com.example.demo.dao.order.OrderEntity;
import com.example.demo.dao.order.OrderRepository;
import com.example.demo.dao.payment.PaymentsEntity;
import com.example.demo.dao.payment.PaymentsRepository;
import com.example.demo.dao.user.UserEntity;
import com.example.demo.dao.user.UserRepository;
import com.example.demo.dto.responses.*;
import com.example.demo.service.impl.UserServiceImpl;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTests {

  @Mock
  private PaymentsRepository paymentRepository;

  @Mock
  private UserRepository userRepository;

  @Mock
  private OrderRepository orderRepository;

  @InjectMocks
  private UserServiceImpl userService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void testAddPhoneWithValidIdAndPhoneNumber() {
    UserEntity user = new UserEntity();
    user.setId(1L);
    when(userRepository.findById(1L)).thenReturn(Optional.of(user));

    ResponseEntity<AddPhoneResponse> response = userService.addPhone(1L, "1234567890");

    assertTrue(Objects.requireNonNull(response.getBody()).isResult());
    verify(userRepository, times(1)).save(user);
  }

  @Test
  void testAddPhoneWithInvalidPhoneNumber() {
    ResponseEntity<AddPhoneResponse> response = userService.addPhone(1L, "invalid_number");

    assertFalse(Objects.requireNonNull(response.getBody()).isResult());
    verifyNoInteractions(userRepository);
  }

  @Test
  void testKafkaListenersListenerOrders1WithValidData() {
    UserEntity user = new UserEntity();
    user.setId(1L);
    when(userRepository.findById(1L)).thenReturn(Optional.of(user));

    PaymentsEntity paymentEntity = new PaymentsEntity();
    paymentEntity.setId(1L);
    when(paymentRepository.findByCardNum("1234567890123456")).thenReturn(Optional.of(paymentEntity));

    String data = "{\"userId\":1,\"cardNum\":\"1234567890123456\",\"cardDate\":\"12/25\",\"cardCVV\":\"123\",\"cost\":500.0,\"address\":\"123 Street\"}";

    assertTrue(userService.new KafkaListeners().listenerOrders1(data));
    verify(orderRepository, times(1)).save(any(OrderEntity.class));
  }

  @Test
  void testKafkaListenersListenerOrders1WithInvalidData() {
    assertFalse(userService.new KafkaListeners().listenerOrders1("invalid_data"));
    verifyNoInteractions(orderRepository);
  }

  @Test
  void testKafkaListenersListenerOrders2WithValidData() {
    UserEntity user = new UserEntity();
    user.setId(1L);
    when(userRepository.findById(1L)).thenReturn(Optional.of(user));

    PaymentsEntity paymentEntity = new PaymentsEntity();
    paymentEntity.setId(1L);
    when(paymentRepository.findByCardNum("1234567890123456")).thenReturn(Optional.of(paymentEntity));

    String data = "{\"userId\":1,\"cardNum\":\"1234567890123456\",\"cardDate\":\"12/25\",\"cardCVV\":\"123\",\"cost\":500.0,\"address\":\"123 Street\"}";

    assertTrue(userService.new KafkaListeners().listenerOrders2(data));
    verify(orderRepository, times(1)).save(any(OrderEntity.class));
  }

  @Test
  void testKafkaListenersListenerOrders2WithInvalidData() {
    assertFalse(userService.new KafkaListeners().listenerOrders2("invalid_data"));
    verifyNoInteractions(orderRepository);
  }
}
