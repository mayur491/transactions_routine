package com.codemayur.transactions_routine.rest.controller;

import com.codemayur.transactions_routine.core.dto.AccountRequestDto;
import com.codemayur.transactions_routine.core.dto.AccountResponseDto;
import com.codemayur.transactions_routine.core.exception.AccountException;
import com.codemayur.transactions_routine.core.exception.AccountNotFoundException;
import com.codemayur.transactions_routine.service.AccountService;
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
@RequestMapping("/api/v1/accounts")
@Slf4j
public class AccountController {

    private final AccountService accountService;

    public AccountController(final AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountResponseDto> getAccount(@PathVariable Long accountId) {
        AccountResponseDto accountResponseDto;
        try {

            accountResponseDto = accountService.getAccountByAccountId(accountId);

        } catch (AccountNotFoundException accountNotFoundException) {
            log.error(accountNotFoundException.getMessage(), accountNotFoundException);
            return ResponseEntity.noContent().build();
        } catch (AccountException e) {
            log.error("Error while fetching account with accountId: {}", accountId, e);
            accountResponseDto = AccountResponseDto.builder()
                                                   .errorCode("AC50001")
                                                   .errorMessage("Internal Server Error")
                                                   .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(accountResponseDto);
        }
        return ResponseEntity.ok(accountResponseDto);
    }

    @PostMapping
    public ResponseEntity<AccountResponseDto> createAccount(@RequestBody AccountRequestDto accountRequestDto) {
        AccountResponseDto accountResponseDto;
        try {

            return ResponseEntity.ok(accountService.createAccount(accountRequestDto));

        } catch (AccountException accountException) {
            log.error(accountException.getMessage(), accountException);
            accountResponseDto = AccountResponseDto.builder()
                                                   .errorCode("AC50002")
                                                   .errorMessage("Internal Server Error")
                                                   .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(accountResponseDto);
        }
    }

}
