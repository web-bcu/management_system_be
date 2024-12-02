package com.csbu.finance.dto;

import com.csbu.finance.model.Budget;

import java.time.LocalDateTime;

public record TransactionDto(
        Integer id,
        String transactionType,
        Integer amount,
        String currency,
        Boolean status,
        LocalDateTime transactionDate,
        String description,
        Integer budget_id
) {
}
