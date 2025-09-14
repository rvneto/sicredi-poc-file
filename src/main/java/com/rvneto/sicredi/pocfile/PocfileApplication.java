package com.rvneto.sicredi.pocfile;

import com.rvneto.sicredi.pocfile.service.PaymentService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PocfileApplication {

    public static void main(String[] args) {
        SpringApplication.run(PocfileApplication.class, args);
    }

    @Autowired
    PaymentService paymentService;

    @PostConstruct
    void t() {
        // paymentService.loadInitialData();
        paymentService.builderFile();
    }

}
