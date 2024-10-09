package com.codemayur.transactions_routine.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "transactions", indexes = {
        @javax.persistence.Index(name = "transactions_account_id_index", columnList = "account_id"),
        @javax.persistence.Index(name = "transactions_operation_type_id_index", columnList = "operation_type_id"),
        @javax.persistence.Index(name = "transactions_account_id_operation_type_id_index", columnList = "account_id, operation_type_id")
})
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long transactionId;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private AccountEntity account;

    @ManyToOne
    @JoinColumn(name = "operation_type_id", nullable = false)
    private OperationTypeEntity operationType;

    @Column(name = "amount", nullable = false, precision = 10, scale = 1)
    private BigDecimal amount;

    @CreationTimestamp
    @Column(name = "event_date", nullable = false, updatable = false)
    private Timestamp eventDate;

}
