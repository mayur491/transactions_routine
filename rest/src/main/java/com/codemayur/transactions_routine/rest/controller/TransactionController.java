package com.codemayur.transactions_routine.rest.controller;

import com.codemayur.transactions_routine.core.bo.TransactionBo;
import com.codemayur.transactions_routine.core.dto.TransactionRequestDto;
import com.codemayur.transactions_routine.core.dto.TransactionResponseDto;
import com.codemayur.transactions_routine.core.exception.AccountNotFoundException;
import com.codemayur.transactions_routine.core.exception.OperationTypeNotFoundException;
import com.codemayur.transactions_routine.core.exception.TransactionException;
import com.codemayur.transactions_routine.core.exception.TransactionNotFoundException;
import com.codemayur.transactions_routine.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

import static com.codemayur.transactions_routine.core.constant.AppURLConstants.API_V1;
import static com.codemayur.transactions_routine.core.constant.AppURLConstants.SLASH;
import static com.codemayur.transactions_routine.core.constant.AppURLConstants.TRANSACTIONS;

@RestController
@RequestMapping("/api/v1/transactions")
@Slf4j
public class TransactionController {

    private final TransactionService transactionService;

    @Value("${transactions.routine.base.url}")
    private String baseUrl;

    public TransactionController(final TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionResponseDto> getTransactionById(@PathVariable Long transactionId) {
        try {
            return ResponseEntity.ok(transactionService.getTransactionById(transactionId));
        } catch (TransactionNotFoundException transactionNotFoundException) {
            log.error(transactionNotFoundException.getMessage(), transactionNotFoundException);
            return ResponseEntity.noContent().build();
        } catch (TransactionException e) {
            log.error("Error while fetching transaction with transactionId: {}", transactionId, e);
            TransactionResponseDto transactionResponseDto =
                    TransactionResponseDto.builder()
                                          .errorCode("TC50001")
                                          .errorMessage("Internal Server Error")
                                          .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(transactionResponseDto);
        }
    }

    @GetMapping("/account-operation-type")
    public ResponseEntity<List<TransactionBo>> getTransactionByAccountIdAndOrOperationTypeId(
            @RequestParam(required = false) Long accountId,
            @RequestParam(required = false) Long operationTypeId) {
        try {
            if (accountId != null && operationTypeId != null) {
                return ResponseEntity.ok(transactionService.getTransactionsByAccountIdAndOperationTypeId(accountId,
                                                                                                         operationTypeId));
            } else if (accountId == null && operationTypeId != null) {
                return ResponseEntity.ok(transactionService.getTransactionsByOperationTypeId(operationTypeId));
            } else if (accountId != null) {
                return ResponseEntity.ok(transactionService.getTransactionsByAccountId(accountId));
            }
            return ResponseEntity.badRequest().body(Collections.emptyList());
        } catch (TransactionException e) {
            log.error("Error while fetching transactions. accountId: {}, operationTypeId: {}",
                      accountId, operationTypeId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Collections.emptyList());
        }
    }

    @PostMapping
    public ResponseEntity<TransactionResponseDto> createTransaction(
            @RequestBody TransactionRequestDto transactionRequestDto) {
        try {

            final TransactionResponseDto transactionResponseDto = transactionService.createTransaction(
                    transactionRequestDto);
            try {
                return ResponseEntity.created(new URI(baseUrl + API_V1 + TRANSACTIONS + SLASH + transactionResponseDto.getTransactionId()))
                                     .body(transactionResponseDto);
            } catch (URISyntaxException e) {
                log.warn("TransactionController::URISyntaxException", e);
                return ResponseEntity.status(HttpStatus.CREATED)
                                     .body(transactionResponseDto);
            }
        } catch (OperationTypeNotFoundException operationTypeNotFoundException) {
            log.error(operationTypeNotFoundException.getMessage(), operationTypeNotFoundException);
            TransactionResponseDto transactionResponseDto =
                    TransactionResponseDto.builder()
                                          .errorCode("TC40001")
                                          .errorMessage("Operation Type not found")
                                          .build();
            return ResponseEntity.badRequest().body(transactionResponseDto);
        } catch (AccountNotFoundException accountNotFoundException) {
            log.error(accountNotFoundException.getMessage(), accountNotFoundException);
            TransactionResponseDto transactionResponseDto =
                    TransactionResponseDto.builder()
                                          .errorCode("TC40002")
                                          .errorMessage("Account not found")
                                          .build();
            return ResponseEntity.badRequest().body(transactionResponseDto);
        } catch (TransactionException transactionException) {
            log.error("Error while creating transaction", transactionException);
            TransactionResponseDto transactionResponseDto =
                    TransactionResponseDto.builder()
                                          .errorCode("TC50002")
                                          .errorMessage("Internal Server Error")
                                          .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(transactionResponseDto);
        }
    }

}
