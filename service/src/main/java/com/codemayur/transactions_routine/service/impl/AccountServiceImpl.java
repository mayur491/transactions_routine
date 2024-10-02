package com.codemayur.transactions_routine.service.impl;

import com.codemayur.transactions_routine.core.dto.AccountRequestDto;
import com.codemayur.transactions_routine.core.dto.AccountResponseDto;
import com.codemayur.transactions_routine.core.entity.AccountEntity;
import com.codemayur.transactions_routine.core.exception.AccountException;
import com.codemayur.transactions_routine.core.exception.AccountNotFoundException;
import com.codemayur.transactions_routine.core.mapper.AccountMapper;
import com.codemayur.transactions_routine.core.repo.AccountRepo;
import com.codemayur.transactions_routine.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepo accountRepo;

    public AccountServiceImpl(final AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    @Override
    public AccountResponseDto getAccountByAccountId(final Long accountId) {
        try {
            final Optional<AccountEntity> optionalAccount = accountRepo.findById(accountId);
            if (!optionalAccount.isPresent()) {
                throw new AccountNotFoundException("Account not found with accountId: " + accountId);
            }
            return AccountMapper.INSTANCE.entityToResponseDto(optionalAccount.get());
        } catch (AccountNotFoundException accountNotFoundException) {
            throw accountNotFoundException;
        } catch (Exception e) {
            throw new AccountException("Error while fetching account with accountId: " + accountId, e);
        }
    }

    @Override
    public AccountResponseDto createAccount(final AccountRequestDto accountRequestDto) {
        try {
            final AccountEntity accountEntity =
                    accountRepo.save(AccountMapper.INSTANCE.requestDtoToEntity(accountRequestDto));
            return AccountMapper.INSTANCE.entityToResponseDto(accountEntity);
        } catch (Exception e) {
            throw new AccountException("Error while creating account", e);
        }
    }

}
