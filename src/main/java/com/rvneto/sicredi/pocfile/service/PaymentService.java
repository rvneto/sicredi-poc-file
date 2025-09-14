package com.rvneto.sicredi.pocfile.service;

import com.rvneto.sicredi.pocfile.domain.enums.Status;
import com.rvneto.sicredi.pocfile.domain.model.Payment;
import com.rvneto.sicredi.pocfile.kafka.KafkaProducer;
import com.rvneto.sicredi.pocfile.repository.ArquivoRepository;
import com.rvneto.sicredi.pocfile.repository.PaymentRepository;
import com.rvneto.sicredi.pocfile.utils.NumberUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
            // Buscar dados
            List<Payment> payments = paymentRepository.findTop999ByStatus(Status.INCLUDED);

            if (payments.isEmpty()) return;

            // Atualizar status para PROCESSING
            updateStatus(payments, Status.PROCESSING);

            // Criar arquivo
            String file = fileService.createFile(payments);
            String fileName = "payments_" + String.format("%05d", arquivoRepository.getNextArquivoSequence());

            // Mover arquivo simulando o s3
            fileService.moveFile(file, fileName);

            // Postar no kafka
            kafkaProducer.sendMessage(fileName);

            // Atualizar status para FINALIZED
            updateStatus(payments, Status.FINALIZED);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateStatus(List<Payment> payments, Status status) {
        payments.forEach(payment -> payment.setStatus(status));
        paymentRepository.saveAll(payments);
    }

    public void loadInitialData() {

        for (int i = 0; i < 1000000; i++) {
            Payment payment = Payment.builder().transactionId(UUID.randomUUID().toString()).value(NumberUtils.generateRandomValue()).status(Status.INCLUDED).build();

            paymentRepository.save(payment);

        }

    }

}
