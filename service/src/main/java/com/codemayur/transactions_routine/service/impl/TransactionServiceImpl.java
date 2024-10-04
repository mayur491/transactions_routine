package com.codemayur.transactions_routine.service.impl;

import com.codemayur.transactions_routine.core.bo.TransactionBo;
import com.codemayur.transactions_routine.core.dto.TransactionRequestDto;
import com.codemayur.transactions_routine.core.dto.TransactionResponseDto;
import com.codemayur.transactions_routine.core.entity.AccountEntity;
import com.codemayur.transactions_routine.core.entity.OperationTypeEntity;
import com.codemayur.transactions_routine.core.entity.TransactionEntity;
import com.codemayur.transactions_routine.core.exception.AccountNotFoundException;
import com.codemayur.transactions_routine.core.exception.OperationTypeNotFoundException;
import com.codemayur.transactions_routine.core.exception.TransactionException;
import com.codemayur.transactions_routine.core.exception.TransactionNotFoundException;
import com.codemayur.transactions_routine.core.mapper.TransactionMapper;
import com.codemayur.transactions_routine.core.repo.AccountRepo;
import com.codemayur.transactions_routine.core.repo.OperationTypeRepo;
import com.codemayur.transactions_routine.core.repo.TransactionRepo;
import com.codemayur.transactions_routine.service.TransactionService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepo transactionRepo;
    private final AccountRepo accountRepo;
    private final OperationTypeRepo operationTypeRepo;

    public TransactionServiceImpl(final TransactionRepo transactionRepo,
                                  final AccountRepo accountRepo,
                                  final OperationTypeRepo operationTypeRepo) {
        this.transactionRepo = transactionRepo;
        this.accountRepo = accountRepo;
        this.operationTypeRepo = operationTypeRepo;
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
    public List<TransactionBo> getTransactionsByAccountId(final Long accountId) {
        try {
            final List<TransactionEntity> transactionEntities = transactionRepo.findByAccount_AccountId(accountId);
            return TransactionMapper.INSTANCE.entityToBo(transactionEntities);
        } catch (Exception e) {
            throw new TransactionException("Error while fetching transactions with accountId: " + accountId, e);
        }
    }

    @Override
    public List<TransactionBo> getTransactionsByOperationTypeId(final Long operationTypeId) {
        try {
            final List<TransactionEntity> transactionEntities = transactionRepo.findByOperationType_OperationTypeId(operationTypeId);
            return TransactionMapper.INSTANCE.entityToBo(transactionEntities);
        } catch (Exception e) {
            throw new TransactionException("Error while fetching transactions with operationTypeId: " + operationTypeId, e);
        }
    }

    @Override
    public List<TransactionBo> getTransactionsByAccountIdAndOperationTypeId(final Long accountId,
                                                                            final Long operationTypeId) {
        try {
            final List<TransactionEntity> transactionEntities =
                    transactionRepo.findByAccount_AccountIdAndOperationType_OperationTypeId(accountId, operationTypeId);
            return TransactionMapper.INSTANCE.entityToBo(transactionEntities);
        } catch (Exception e) {
            throw new TransactionException("Error while fetching transactions with accountId: " + accountId +
                                           " and operationTypeId: " + operationTypeId, e);
        }
    }

    @Override
    public TransactionResponseDto createTransaction(final TransactionRequestDto transactionRequestDto) {
        try {
            final OperationTypeEntity operationTypeEntity =
                    operationTypeRepo.findById(transactionRequestDto.getOperationType().getOperationTypeId())
                                     .orElseThrow(() -> new OperationTypeNotFoundException("OperationType not found " +
                                                                                           "with operationTypeId: " + transactionRequestDto.getOperationType().getOperationTypeId()));

            final AccountEntity accountEntity =
                    accountRepo.findById(transactionRequestDto.getAccount().getAccountId())
                               .orElseThrow(() -> new AccountNotFoundException("Account not found with accountId: " + transactionRequestDto.getAccount().getAccountId()));

            TransactionEntity transactionEntity = TransactionMapper.INSTANCE.requestDtoToEntity(transactionRequestDto);
            transactionEntity.setEventDate(new Timestamp(System.nanoTime()));
            transactionEntity.setAccount(accountEntity);
            transactionEntity.setOperationType(operationTypeEntity);
            transactionEntity = transactionRepo.save(transactionEntity);
            return TransactionMapper.INSTANCE.entityToResponseDto(transactionEntity);
        } catch (OperationTypeNotFoundException | AccountNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new TransactionException("Error while creating transaction", e);
        }
    }

}
