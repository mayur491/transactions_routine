package com.codemayur.transactions_routine.service;

import com.codemayur.transactions_routine.core.bo.TransactionBo;
import com.codemayur.transactions_routine.core.dto.TransactionRequestDto;
import com.codemayur.transactions_routine.core.dto.TransactionResponseDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface TransactionService {

    @Transactional(readOnly = true)
    TransactionResponseDto getTransactionById(Long transactionId);

    @Transactional(readOnly = true)
    List<TransactionBo> getTransactionsByAccountId(Long accountId);

    @Transactional(readOnly = true)
    List<TransactionBo> getTransactionsByOperationTypeId(Long operationTypeId);

    @Transactional(readOnly = true)
    List<TransactionBo> getTransactionsByAccountIdAndOperationTypeId(Long accountId, Long operationTypeId);

    TransactionResponseDto createTransaction(TransactionRequestDto transactionRequestDto);

}
