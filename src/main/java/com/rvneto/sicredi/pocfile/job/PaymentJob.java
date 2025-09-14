package com.rvneto.sicredi.pocfile.job;

import com.rvneto.sicredi.pocfile.service.PaymentService;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentJob {

    private final PaymentService paymentService;

    @Scheduled(cron = "0 0/1 * * * ?") // a cada 1 minuto
    @SchedulerLock(name = "paymentJob", lockAtMostFor = "PT10M", lockAtLeastFor = "PT5M")
    public void processJob() {
        // Lógica do job aqui
        System.out.println("Iniciando job");
        paymentService.builderFile();
        System.out.println("Job finalizado");
    }

}
