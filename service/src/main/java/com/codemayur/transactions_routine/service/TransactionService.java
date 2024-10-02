package com.codemayur.transactions_routine.service;

import com.codemayur.transactions_routine.core.dto.TransactionRequestDto;
import com.codemayur.transactions_routine.core.dto.TransactionResponseDto;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TransactionService {

    @Transactional(readOnly = true)
    TransactionResponseDto getTransactionById(Long transactionId);

    @Transactional(readOnly = true)
    TransactionResponseDto getTransactionByIdWithAccountAndOperationType(Long transactionId);

    TransactionResponseDto createTransaction(TransactionRequestDto transactionRequestDto);

}
