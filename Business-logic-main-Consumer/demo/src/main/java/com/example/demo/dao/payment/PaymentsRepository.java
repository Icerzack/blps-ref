package com.example.demo.dao.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentsRepository extends JpaRepository<PaymentsEntity, Long> {
    @Query("SELECT p FROM PaymentsEntity p WHERE p.cardNum = :cardNum")
    Optional<PaymentsEntity> findByCardNum(@Param("cardNum") String cardNum);

}
