package com.codemayur.transactions_routine.core.mapper;

import com.codemayur.transactions_routine.core.bo.TransactionBo;
import com.codemayur.transactions_routine.core.dto.TransactionRequestDto;
import com.codemayur.transactions_routine.core.dto.TransactionResponseDto;
import com.codemayur.transactions_routine.core.entity.TransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    TransactionBo entityToBo(final TransactionEntity transactionEntity);

    List<TransactionBo> entityToBo(final List<TransactionEntity> transactionEntityList);

    TransactionEntity boToEntity(final TransactionBo transactionBo);

    List<TransactionEntity> boToEntity(final List<TransactionBo> transactionBoList);

    TransactionResponseDto entityToResponseDto(final TransactionEntity transactionEntity);

    TransactionEntity requestDtoToEntity(final TransactionRequestDto transactionRequestDto);

    TransactionResponseDto boToResponseDto(final TransactionBo transactionBo);

}
