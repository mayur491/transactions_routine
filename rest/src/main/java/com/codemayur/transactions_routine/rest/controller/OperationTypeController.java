package com.codemayur.transactions_routine.rest.controller;

import com.codemayur.transactions_routine.core.bo.OperationTypeBo;
import com.codemayur.transactions_routine.core.exception.OperationTypeException;
import com.codemayur.transactions_routine.core.exception.OperationTypeNotFoundException;
import com.codemayur.transactions_routine.service.OperationTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Profile({ "local", "stage" })
@RestController
@RequestMapping("/api/v1/operation-type")
@Slf4j
public class OperationTypeController {

    private final OperationTypeService operationTypeService;

    public OperationTypeController(final OperationTypeService operationTypeService) {
        this.operationTypeService = operationTypeService;
    }

    @GetMapping
    public ResponseEntity<List<OperationTypeBo>> getOperationType() {
        try {
            return ResponseEntity.ok(operationTypeService.getAllOperationTypes());
        } catch (OperationTypeNotFoundException operationTypeNotFoundException) {
            log.error(operationTypeNotFoundException.getMessage(), operationTypeNotFoundException);
            return ResponseEntity.noContent().build();
        } catch (OperationTypeException e) {
            log.error("Error while fetching operation types", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{operationTypeId}")
    public ResponseEntity<OperationTypeBo> getOperationTypeById(@PathVariable Long operationTypeId) {
        try {
            return ResponseEntity.ok(operationTypeService.getOperationTypeById(operationTypeId));
        } catch (OperationTypeNotFoundException operationTypeNotFoundException) {
            log.error(operationTypeNotFoundException.getMessage(), operationTypeNotFoundException);
            return ResponseEntity.noContent().build();
        } catch (OperationTypeException e) {
            log.error("Error while fetching operation type with operationTypeId: {}", operationTypeId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<OperationTypeBo> createOperationType(@RequestBody OperationTypeBo operationTypeBo) {
        try {
            return ResponseEntity.ok(operationTypeService.createOperationType(operationTypeBo));
        } catch (OperationTypeException e) {
            log.error("Error while creating operation type", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
