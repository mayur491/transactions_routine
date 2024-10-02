package com.codemayur.transactions_routine.service;

import com.codemayur.transactions_routine.core.bo.OperationTypeBo;
import com.codemayur.transactions_routine.core.exception.OperationTypeException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface OperationTypeService {

    @Transactional(readOnly = true)
    List<OperationTypeBo> getAllOperationTypes() throws OperationTypeException;

    @Transactional(readOnly = true)
    OperationTypeBo getOperationTypeById(Long operationTypeId) throws OperationTypeException;

    OperationTypeBo createOperationType(OperationTypeBo operationTypeBo) throws OperationTypeException;

}
