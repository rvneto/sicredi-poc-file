package com.rvneto.sicredi.pocfile.domain.model;

import com.rvneto.sicredi.pocfile.domain.enums.Status;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@Entity
@Table(name = "payments", uniqueConstraints = {
        @UniqueConstraint(name = "uk_payments_transaction_id", columnNames = "transaction_id")
})
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", precision = 38, nullable = false)
    private Long id;

    @Column(name = "transaction_id", length = 250, nullable = false, unique = true)
    private String transactionId;

    @Column(name = "value", precision = 38, scale = 2, nullable = false)
    private BigDecimal value;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 10, nullable = false)
    private Status status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "insert_timestamp", nullable = false, updatable = false, insertable = false,
            columnDefinition = "DATE DEFAULT SYSDATE")
    private Date insertTimestamp;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_timestamp")
    private Date updateTimestamp;

}
