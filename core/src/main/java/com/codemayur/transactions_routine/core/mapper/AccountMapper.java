package com.codemayur.transactions_routine.core.mapper;

import com.codemayur.transactions_routine.core.bo.AccountBo;
import com.codemayur.transactions_routine.core.dto.AccountRequestDto;
import com.codemayur.transactions_routine.core.dto.AccountResponseDto;
import com.codemayur.transactions_routine.core.entity.AccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    AccountBo entityToBo(final AccountEntity accountEntity);

    AccountEntity boToEntity(final AccountBo accountBo);

    AccountResponseDto entityToResponseDto(final AccountEntity accountEntity);

    AccountResponseDto boToResponseDto(final AccountBo accountBo);

    AccountEntity requestDtoToEntity(final AccountRequestDto accountResponseDto);

}
