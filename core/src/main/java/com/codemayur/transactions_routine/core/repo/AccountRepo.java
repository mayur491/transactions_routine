package com.codemayur.transactions_routine.core.repo;

import com.codemayur.transactions_routine.core.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepo extends JpaRepository<AccountEntity, Long> {

}
