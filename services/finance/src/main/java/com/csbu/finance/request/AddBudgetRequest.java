package com.csbu.finance.request;

import com.csbu.finance.model.Transaction;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class AddBudgetRequest {
    @NotNull
    private LocalDate periodStart;
    @NotNull
    private LocalDate periodEnd;
    @NotNull
    private Integer budgetAmount;
    @NotNull
    private String currency;
}
