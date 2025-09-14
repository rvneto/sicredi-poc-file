package com.rvneto.sicredi.pocfile.repository;

import com.rvneto.sicredi.pocfile.domain.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
