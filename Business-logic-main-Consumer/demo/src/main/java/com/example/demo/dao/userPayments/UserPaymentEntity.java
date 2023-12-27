package com.example.demo.dao.userPayments;

import com.example.demo.dao.payment.PaymentsEntity;
import com.example.demo.dao.user.UserEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_payments")
public class UserPaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "payment_id", referencedColumnName = "id", nullable = false)
    private PaymentsEntity payment;

}
