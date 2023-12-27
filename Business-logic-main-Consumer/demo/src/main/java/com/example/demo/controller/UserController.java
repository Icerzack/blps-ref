package com.example.demo.controller;

import com.example.demo.dto.requests.AddPaymentRequest;
import com.example.demo.dto.requests.AddPhoneRequest;
import com.example.demo.dto.responses.*;
import com.example.demo.service.impl.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "Transaction", description = "Эндпоинты для взаимодействия с данными пользователя")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @PutMapping("/v1/user-management/add-phone")
    @ApiOperation(value = "Добавить номер телефона для пользователя")
    public ResponseEntity<AddPhoneResponse> addPhone(@RequestBody AddPhoneRequest request) {
        long id = request.getUserId();
        String phone = request.getPhoneNumber();
        return userServiceImpl.addPhone(id, phone);
    }

    @PutMapping("/v1/user/user-management/add-payment")
    @ApiOperation(value = "Добавить карту для пользователя")
    public ResponseEntity<AddPaymentResponse> addPayment(@RequestBody AddPaymentRequest request) {
        long userId = request.getUserId();
        String cardNum = request.getCardNum();
        String cardDate = request.getCardDate();
        String cardCVV = request.getCardCVV();
        return userServiceImpl.addPayment((long) userId, cardNum, cardDate, cardCVV);
    }

    @GetMapping("/v1/user/user-management/check-payment")
    @ApiOperation(value = "Проверить доступный способ оплаты для пользователя")
    public ResponseEntity<CheckPaymentResponse> checkPayment(@RequestParam int id) {
        return userServiceImpl.checkPayment((long) id);
    }

    @GetMapping("/v1/user/user-management/check-phone")
    @ApiOperation(value = "Проверить привязанный номер телефона для пользователя")
    public ResponseEntity<CheckPhoneResponse> checkPhone(@RequestParam int id) {
        return userServiceImpl.checkPhone((long) id);
    }

    @DeleteMapping("/v1/admin/user-management/delete-payment")
    @ApiOperation(value = "Удалить доступный способ оплаты для пользователя")
    public ResponseEntity<DeletePaymentResponse> deletePayment(@RequestParam int id) {
        return userServiceImpl.deletePayment((long) id);
    }

    @DeleteMapping("/v1/admin/user-management/delete-phone")
    @ApiOperation(value = "Удалить привязанный номер телефона для пользователя")
    public ResponseEntity<DeletePhoneResponse> deletePhone(@RequestParam int id) {
        return userServiceImpl.deletePhone((long) id);
    }
}
