package com.example.demo.controller;

import com.example.demo.dto.Requests.PerformPaymentRequest;
import com.example.demo.dto.Responses.CheckSmsResponse;
import com.example.demo.dto.Responses.CheckSumResponse;
import com.example.demo.dto.Responses.PerformPaymentResponse;
import com.example.demo.service.impl.OrderServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "Order", description = "Эндпоинты для взаимодействия с заказом")
public class OrderController {
    @Autowired
    private OrderServiceImpl orderServiceImpl;

    @GetMapping("/v1/order-management/check-sum")
    @ApiOperation(value = "Проверяет, что сумма больше 100")
    public ResponseEntity<CheckSumResponse> checkSum(@RequestParam int id, @RequestParam int sum) {
        return orderServiceImpl.checkSum((long) id, sum);
    }

    @GetMapping("/v1/order-management/check-sms")
    @ApiOperation(value = "Проверяет, что введенный код смс верен для данного номера")
    public ResponseEntity<CheckSmsResponse> checkSms(@RequestParam int id, @RequestParam String phone, @RequestParam String sms) {
        return orderServiceImpl.checkSms(id, phone, sms);
    }

    @PostMapping("/v1/order-management/perform-payment")
    @ApiOperation(value = "Осуществляет покупку с указанными данными карты")
    public ResponseEntity<PerformPaymentResponse> performPayment(@RequestBody PerformPaymentRequest request) {
        long userId = request.getUserId();
        String cardNum = request.getCardNum();
        String cardDate = request.getCardDate();
        String cardCVV = request.getCardCVV();
        Double cost = request.getCost();
        String address = request.getAddress();
        return orderServiceImpl.performPayment(userId, cardNum, cardDate, cardCVV, cost, address);
    }
}
