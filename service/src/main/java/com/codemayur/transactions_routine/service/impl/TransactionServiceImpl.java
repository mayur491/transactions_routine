package com.codemayur.transactions_routine.service.impl;

import com.codemayur.transactions_routine.core.bo.OperationTypeBo;
import com.codemayur.transactions_routine.core.dto.AccountResponseDto;
import com.codemayur.transactions_routine.core.dto.TransactionRequestDto;
import com.codemayur.transactions_routine.core.dto.TransactionResponseDto;
import com.codemayur.transactions_routine.core.entity.TransactionEntity;
import com.codemayur.transactions_routine.core.exception.TransactionException;
import com.codemayur.transactions_routine.core.exception.TransactionNotFoundException;
import com.codemayur.transactions_routine.core.mapper.TransactionMapper;
import com.codemayur.transactions_routine.core.repo.TransactionRepo;
import com.codemayur.transactions_routine.service.AccountService;
import com.codemayur.transactions_routine.service.OperationTypeService;
import com.codemayur.transactions_routine.service.TransactionService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepo transactionRepo;
    private final AccountService accountService;
    private final OperationTypeService operationTypeService;

    public TransactionServiceImpl(final TransactionRepo transactionRepo,
                                  final AccountService accountService,
                                  final OperationTypeService operationTypeService) {
        this.transactionRepo = transactionRepo;
        this.accountService = accountService;
        this.operationTypeService = operationTypeService;
    }

    @Override
    public TransactionResponseDto getTransactionById(final Long transactionId) {
        try {
            final Optional<TransactionEntity> transactionEntityOptional = transactionRepo.findById(transactionId);
            if (!transactionEntityOptional.isPresent()) {
                throw new TransactionNotFoundException("Transaction not found with transactionId: " + transactionId);
            }
            return TransactionMapper.INSTANCE.entityToResponseDto(transactionEntityOptional.get());
        } catch (TransactionNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new TransactionException("Error while fetching transaction with transactionId: " + transactionId, e);
        }
    }

    @Override
    public TransactionResponseDto getTransactionByIdWithAccountAndOperationType(final Long transactionId) {
        try {
            final Optional<TransactionEntity> transactionEntityOptional = transactionRepo.findById(transactionId);
            if (!transactionEntityOptional.isPresent()) {
                throw new TransactionNotFoundException("Transaction not found with transactionId: " + transactionId);
            }
            final TransactionResponseDto transactionResponseDto =
                    TransactionMapper.INSTANCE.entityToResponseDto(transactionEntityOptional.get());

            // get operation type
            final OperationTypeBo operationType =
                    operationTypeService.getOperationTypeById(transactionResponseDto.getOperationTypeId());
            transactionResponseDto.setOperationTypeBo(operationType);
            transactionResponseDto.setOperationTypeId(null);

            // get account
            final AccountResponseDto account =
                    accountService.getAccountByAccountId(transactionResponseDto.getAccountId());
            transactionResponseDto.setAccountResponseDto(account);
            transactionResponseDto.setAccountId(null);

            return transactionResponseDto;
        } catch (TransactionNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new TransactionException("Error while fetching transaction with transactionId: " + transactionId, e);
        }
    }

    @Override
    public TransactionResponseDto createTransaction(final TransactionRequestDto transactionRequestDto) {
        try {
            TransactionEntity transactionEntity = TransactionMapper.INSTANCE.requestDtoToEntity(transactionRequestDto);
            transactionEntity.setEventDate(new java.sql.Timestamp(System.nanoTime()));
            transactionEntity = transactionRepo.save(transactionEntity);
            return TransactionMapper.INSTANCE.entityToResponseDto(transactionEntity);
        } catch (Exception e) {
            throw new TransactionException("Error while creating transaction", e);
        }
    }

}
