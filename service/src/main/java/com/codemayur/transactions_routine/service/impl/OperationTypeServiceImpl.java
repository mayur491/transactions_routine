package com.codemayur.transactions_routine.service.impl;

import com.codemayur.transactions_routine.core.bo.OperationTypeBo;
import com.codemayur.transactions_routine.core.entity.OperationTypeEntity;
import com.codemayur.transactions_routine.core.exception.OperationTypeException;
import com.codemayur.transactions_routine.core.exception.OperationTypeNotFoundException;
import com.codemayur.transactions_routine.core.mapper.OperationTypeMapper;
import com.codemayur.transactions_routine.core.repo.OperationTypeRepo;
import com.codemayur.transactions_routine.service.OperationTypeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OperationTypeServiceImpl implements OperationTypeService {

    private final OperationTypeRepo operationTypeRepo;

    public OperationTypeServiceImpl(OperationTypeRepo operationTypeRepo) {
        this.operationTypeRepo = operationTypeRepo;
    }

    @Override
    public List<OperationTypeBo> getAllOperationTypes() throws OperationTypeException {
        try {
            final List<OperationTypeEntity> operationTypeEntityList = operationTypeRepo.findAll();
            if (operationTypeEntityList.isEmpty()) {
                throw new OperationTypeNotFoundException("No operation types found");
            }
            return OperationTypeMapper.INSTANCE.entityToBo(operationTypeEntityList);
        } catch (Exception e) {
            throw new OperationTypeException("Error while fetching operation types", e);
        }
    }

    @Override
    public OperationTypeBo getOperationTypeById(final Long operationTypeId) throws OperationTypeException {
        try {
            final Optional<OperationTypeEntity> operationTypeEntityOptional = operationTypeRepo.findById(operationTypeId);
            if (!operationTypeEntityOptional.isPresent()) {
                throw new OperationTypeNotFoundException("Operation type not found with operationTypeId: " + operationTypeId);
            }
            return OperationTypeMapper.INSTANCE.entityToBo(operationTypeEntityOptional.get());
        } catch (OperationTypeNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new OperationTypeException("Error while fetching operation type with operationTypeId: " + operationTypeId, e);
        }
    }

    @Override
    public OperationTypeBo createOperationType(final OperationTypeBo operationTypeBo) throws OperationTypeException {
        try {
            final OperationTypeEntity operationTypeEntity = operationTypeRepo.save(OperationTypeMapper.INSTANCE.boToEntity(operationTypeBo));
            return OperationTypeMapper.INSTANCE.entityToBo(operationTypeEntity);
        } catch (Exception e) {
            throw new OperationTypeException("Error while creating operation type", e);
        }
    }

}
