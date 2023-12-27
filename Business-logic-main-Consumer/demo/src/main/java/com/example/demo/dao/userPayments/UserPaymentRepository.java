package com.example.demo.dao.userPayments;

import com.example.demo.dao.payment.PaymentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserPaymentRepository extends JpaRepository<UserPaymentEntity, Long> {
    @Query("SELECT up.payment FROM UserPaymentEntity up WHERE up.user.id = :id")
    Optional<PaymentsEntity> findPaymentByUserId(@Param("id") Long id);

    @Query("SELECT up FROM UserPaymentEntity up WHERE up.user.id = :id")
    Optional<UserPaymentEntity> findByUserId(@Param("id") Long id);

}
