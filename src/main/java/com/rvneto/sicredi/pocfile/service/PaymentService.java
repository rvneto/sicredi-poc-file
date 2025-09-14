package com.rvneto.sicredi.pocfile.service;

import com.rvneto.sicredi.pocfile.domain.enums.Status;
import com.rvneto.sicredi.pocfile.domain.model.Payment;
import com.rvneto.sicredi.pocfile.kafka.KafkaProducer;
import com.rvneto.sicredi.pocfile.repository.ArquivoRepository;
import com.rvneto.sicredi.pocfile.repository.PaymentRepository;
import com.rvneto.sicredi.pocfile.utils.NumberUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ArquivoRepository arquivoRepository;
    private final KafkaProducer kafkaProducer;
    private final FileService fileService;

    public void builderFile() {
        try {
            // 1 buscar dados
            List<Payment> payments = paymentRepository.findTop999ByStatus(Status.INCLUDED);

            // 2 criar arquivo
            String file = fileService.createFile(payments);
            String fileName = "payments_" + String.format("%05d", arquivoRepository.getNextArquivoSequence());

            // 3 mover arquivo simulando o s3
            fileService.moveFile(file, fileName);

            // 4 postar no kafka
            kafkaProducer.sendMessage(fileName);

            // 5 atualizar status
            updateStatus(payments, Status.FINALIZED);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateStatus(List<Payment> payments, Status status) {
        payments.forEach(payment -> payment.setStatus(Status.FINALIZED));
        paymentRepository.saveAll(payments);
    }

    public void loadInitialData() {

        for (int i = 0; i < 1000000; i++) {
            Payment payment = Payment.builder().transactionId(UUID.randomUUID().toString()).value(NumberUtils.generateRandomValue()).status(Status.INCLUDED).build();

            paymentRepository.save(payment);

        }

    }

}
