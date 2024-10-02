package com.codemayur.transactions_routine.rest.controller;

import com.codemayur.transactions_routine.core.dto.TransactionRequestDto;
import com.codemayur.transactions_routine.core.dto.TransactionResponseDto;
import com.codemayur.transactions_routine.core.exception.TransactionException;
import com.codemayur.transactions_routine.core.exception.TransactionNotFoundException;
import com.codemayur.transactions_routine.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transactions")
@Slf4j
public class TransactionController {

    private final TransactionService transactionService;

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
            TransactionResponseDto transactionResponseDto = TransactionResponseDto.builder()
                                                                                  .errorCode("TC50001")
                                                                                  .errorMessage("Internal Server Error")
                                                                                  .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(transactionResponseDto);
        }
    }

    @PostMapping
    public ResponseEntity<TransactionResponseDto> createTransaction(
            @RequestBody TransactionRequestDto transactionRequestDto) {
        try {

            return ResponseEntity.ok(transactionService.createTransaction(transactionRequestDto));

        } catch (TransactionException transactionException) {
            log.error("Error while creating transaction", transactionException);
            TransactionResponseDto transactionResponseDto = TransactionResponseDto.builder()
                                                                                  .errorCode("TC50002")
                                                                                  .errorMessage("Internal Server Error")
                                                                                  .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(transactionResponseDto);
        }
    }

}
