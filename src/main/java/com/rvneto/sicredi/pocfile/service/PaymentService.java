package com.rvneto.sicredi.pocfile.service;

import com.rvneto.sicredi.pocfile.domain.enums.Status;
import com.rvneto.sicredi.pocfile.domain.model.Payment;
import com.rvneto.sicredi.pocfile.repository.PaymentRepository;
import com.rvneto.sicredi.pocfile.utils.NumberUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class PaymentService {

    private final PaymentRepository repository;

    public void loadInitialData() {

        for (int i = 0; i < 1000000; i++) {
            Payment payment = Payment.builder()
                    .transactionId(UUID.randomUUID().toString())
                    .value(NumberUtils.generateRandomValue())
                    .status(Status.INCLUDED)
                    .build();

            repository.save(payment);

        }

    }

}
