package com.example.demo.service.impl;

import com.example.demo.dto.Requests.PerformPaymentRequest;
import com.example.demo.dto.Responses.CheckSmsResponse;
import com.example.demo.dto.Responses.CheckSumResponse;
import com.example.demo.dto.Responses.PerformPaymentResponse;
import com.example.demo.service.OrderService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
  private KafkaTemplate<String, PerformPaymentRequest> kafkaTemplate;

  public OrderServiceImpl(KafkaTemplate<String, PerformPaymentRequest> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public ResponseEntity<CheckSumResponse> checkSum(Long orderId, double sumThreshold) {
    CheckSumResponse checkSumResponse = new CheckSumResponse();
    boolean isSumValid = sumValidation(sumThreshold);
    checkSumResponse.setResult(isSumValid);
    return ResponseEntity.ok(checkSumResponse);
  }

  private boolean sumValidation(double sum) {
    return sum > 100;
  }

  public ResponseEntity<CheckSmsResponse> checkSms(Integer orderId, String phoneNumber,
                                                   String sms) {
    CheckSmsResponse checkSmsResponse = new CheckSmsResponse();
    boolean isSmsValid = validateSms(orderId, phoneNumber, sms);
    checkSmsResponse.setResult(isSmsValid);
    return ResponseEntity.ok(checkSmsResponse);
  }

  private boolean validateSms(Integer orderId, String phoneNumber, String sms) {
    String sha256sms = DigestUtils.sha256Hex(orderId + phoneNumber).replaceAll("[^0-9]", "");
    String lastFourDigits = StringUtils.right(sha256sms, 4);
    return sms.equals(lastFourDigits);
  }

  public ResponseEntity<PerformPaymentResponse> performPayment(long userId, String cardNum,
                                                               String cardDate, String cardCVV,
                                                               Double cost, String address) {
    PerformPaymentResponse performPaymentResponse = new PerformPaymentResponse();
    boolean isValidPayment = validatePaymentDetails(cardNum, cardCVV);

    if (!isValidPayment) {
      performPaymentResponse.setResult(false);
      return ResponseEntity.ok(performPaymentResponse);
    }

    PerformPaymentRequest performPaymentRequest =
        new PerformPaymentRequest(userId, cardNum, cardDate, cardCVV, cost, address);
    kafkaTemplate.send("perform-test3", performPaymentRequest);
    performPaymentResponse.setResult(true);
    return ResponseEntity.ok(performPaymentResponse);
  }

  private boolean validatePaymentDetails(String cardNum, String cardCVV) {
    return cardNum.matches("[-+]?\\d+") && cardNum.length() >= 13 && cardNum.length() <= 19
        && cardCVV.matches("[-+]?\\d+") && cardCVV.length() == 3;
  }
}
