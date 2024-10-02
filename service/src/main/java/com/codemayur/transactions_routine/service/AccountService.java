package com.codemayur.transactions_routine.service;

import com.codemayur.transactions_routine.core.dto.AccountRequestDto;
import com.codemayur.transactions_routine.core.dto.AccountResponseDto;
import com.codemayur.transactions_routine.core.exception.AccountException;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AccountService {

    @Transactional(readOnly = true)
    AccountResponseDto getAccountByAccountId(Long accountId) throws AccountException;

    AccountResponseDto createAccount(AccountRequestDto accountRequestDto) throws AccountException;

}
