package com.project.busticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.busticket.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {

}
