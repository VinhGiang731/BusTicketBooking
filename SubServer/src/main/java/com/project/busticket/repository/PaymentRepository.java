package com.project.busticket.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.busticket.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p")
    BigDecimal calculateTotalAmount();
}
