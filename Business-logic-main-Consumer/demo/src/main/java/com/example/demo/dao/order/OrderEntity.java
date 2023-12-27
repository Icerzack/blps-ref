package com.example.demo.dao.order;

import com.example.demo.dao.user.UserEntity;
import com.example.demo.dto.PaymentType;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;

    @Column(name = "order_date", nullable = false)
    private Timestamp orderDate;

    @Column(name = "address", nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type", columnDefinition = "paymentType", nullable = false)
    private PaymentType paymentType;

    @Column(name = "payment_id")
    private Long paymentId;

    @Column(name = "cost")
    private Double cost;
}
