package com.rvneto.sicredi.pocfile.repository;

import com.rvneto.sicredi.pocfile.domain.enums.Status;
import com.rvneto.sicredi.pocfile.domain.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findTop999ByStatus(Status status);

}
