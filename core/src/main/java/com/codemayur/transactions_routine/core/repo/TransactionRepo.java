package com.codemayur.transactions_routine.core.repo;

import com.codemayur.transactions_routine.core.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<TransactionEntity, Long> {

    List<TransactionEntity> findByAccount_AccountId(Long accountId);

    List<TransactionEntity> findByOperationType_OperationTypeId(Long operationTypeId);

    List<TransactionEntity> findByAccount_AccountIdAndOperationType_OperationTypeId(Long accountId,
                                                                                    Long operationTypeId);

}
