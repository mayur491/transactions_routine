package com.codemayur.transactions_routine.core.mapper;

import com.codemayur.transactions_routine.core.bo.TransactionBo;
import com.codemayur.transactions_routine.core.dto.TransactionRequestDto;
import com.codemayur.transactions_routine.core.dto.TransactionResponseDto;
import com.codemayur.transactions_routine.core.entity.TransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    TransactionBo entityToBo(final TransactionEntity transactionEntity);

    TransactionEntity boToEntity(final TransactionBo transactionBo);

    TransactionResponseDto entityToResponseDto(final TransactionEntity transactionEntity);

    TransactionResponseDto boToResponseDto(final TransactionBo transactionBo);

    TransactionEntity requestDtoToEntity(final TransactionRequestDto transactionRequestDto);

}
