package com.rvneto.sicredi.pocfile.service;

import com.rvneto.sicredi.pocfile.domain.model.Payment;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.nio.file.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FileService {

    @Value("${file.destination.dir}")
    private String destinationDir;

    private final ResourceLoader resourceLoader;

    public String createFile(List<Payment> payments) {
        StringBuilder builder = new StringBuilder();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("pt", "BR"));
        DecimalFormat df = new DecimalFormat("##,###.00", symbols);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (Payment payment : payments) {
            builder.append(payment.getTransactionId());
            builder.append(" ");
            builder.append(payment.getInsertTimestamp().toLocalDate().format(formatter));
            builder.append(" ");
            builder.append(String.format("%8s", df.format(payment.getValue())));
            builder.append("\n");
        }
        return builder.toString();
    }

    public void moveFile(String sourceContent, String fileName) throws IOException {
        // Normaliza o caminho do diretório de destino
        Path destDirPath = Path.of(destinationDir).toAbsolutePath().normalize();

        // Gera o path completo do arquivo destino
        Path destFilePath = destDirPath.resolve(fileName).normalize();

        // Cria diretórios caso não existam
        Files.createDirectories(destDirPath);

        // Grava o conteúdo recebido em sourceContent no arquivo destino
        Files.writeString(destFilePath, sourceContent, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

}
