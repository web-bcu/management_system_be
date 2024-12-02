package com.csbu.finance.Repository;

import com.csbu.finance.model.Transaction;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    @Modifying
    @Transactional
    @Query("UPDATE Transaction a SET a.status = true WHERE a.id = :id")
    void updateTransactionStatus(@Param("id") Integer id);
}
