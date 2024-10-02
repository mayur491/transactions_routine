package com.codemayur.transactions_routine.core.mapper;

import com.codemayur.transactions_routine.core.bo.OperationTypeBo;
import com.codemayur.transactions_routine.core.entity.OperationTypeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface OperationTypeMapper {

    OperationTypeMapper INSTANCE = Mappers.getMapper(OperationTypeMapper.class);

    OperationTypeBo entityToBo(OperationTypeEntity operationTypeEntity);

    OperationTypeEntity boToEntity(OperationTypeBo operationTypeBo);

    List<OperationTypeBo> entityToBo(List<OperationTypeEntity> operationTypeEntities);

    List<OperationTypeEntity> boToEntity(List<OperationTypeBo> operationTypeBos);

}
