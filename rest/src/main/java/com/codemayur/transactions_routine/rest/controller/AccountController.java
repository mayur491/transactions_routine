package com.codemayur.transactions_routine.rest.controller;

import com.codemayur.transactions_routine.core.dto.AccountRequestDto;
import com.codemayur.transactions_routine.core.dto.AccountResponseDto;
import com.codemayur.transactions_routine.core.exception.AccountException;
import com.codemayur.transactions_routine.core.exception.AccountNotFoundException;
import com.codemayur.transactions_routine.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

import static com.codemayur.transactions_routine.core.constant.AppURLConstants.ACCOUNTS;
import static com.codemayur.transactions_routine.core.constant.AppURLConstants.API_V1;
import static com.codemayur.transactions_routine.core.constant.AppURLConstants.SLASH;

@RestController
@RequestMapping("/api/v1/accounts")
@Slf4j
public class AccountController {

    private final AccountService accountService;

    @Value("${transactions.routine.base.url}")
    private String baseUrl;

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
            accountResponseDto = accountService.createAccount(accountRequestDto);
            try {
                return ResponseEntity.created(new URI(baseUrl + API_V1 + ACCOUNTS + SLASH + accountResponseDto.getAccountId()))
                                     .body(accountResponseDto);
            } catch (URISyntaxException e) {
                log.warn("AccountController::URISyntaxException", e);
                return ResponseEntity.status(HttpStatus.CREATED)
                                     .body(accountResponseDto);
            }
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
