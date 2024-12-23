package com.csbu.finance.Repository;

import com.csbu.finance.model.Transaction;
import jakarta.transaction.Transactional;
import org.hibernate.query.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    @Modifying
    @Transactional
    @Query("UPDATE Transaction a SET a.status = true WHERE a.id = :id")
    void updateTransactionStatus(@Param("id") String id);

}
