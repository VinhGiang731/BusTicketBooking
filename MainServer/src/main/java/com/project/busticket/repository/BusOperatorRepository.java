package com.project.busticket.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.busticket.entity.BusOperator;

@Repository
public interface BusOperatorRepository extends JpaRepository<BusOperator, String> {
    boolean existsByBusOperatorName(String busOperatorName);

    Optional<BusOperator> findByBusOperatorName(String busOperatorName);

    Optional<BusOperator> findByBusOperatorId(String busOperatorId);
}
