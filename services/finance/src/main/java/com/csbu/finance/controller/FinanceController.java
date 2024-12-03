package com.csbu.finance.controller;

import com.csbu.finance.Service.BudgetService;
import com.csbu.finance.Service.TransactionService;
import com.csbu.finance.dto.BudgetDto;
import com.csbu.finance.dto.TransactionDto;
import com.csbu.finance.model.Budget;
import com.csbu.finance.model.Transaction;
import com.csbu.finance.request.AddBudgetRequest;
import com.csbu.finance.request.AddTransactionRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/finance")
@RequiredArgsConstructor
public class FinanceController {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private BudgetService budgetService;

    @PostMapping("/transactions")
    public ResponseEntity<String> createTransaction(@RequestBody @Valid AddTransactionRequest request)
    {
        Budget budget = budgetService.getBudgetById(request.getBudgetId());
        Transaction transaction = new Transaction();
        transaction.setTransactionType(request.getTransactionType());
        transaction.setAmount(request.getAmount());
        transaction.setCurrency(request.getCurrency());
        transaction.setStatus(request.getStatus());
        transaction.setDescription(request.getDescription());
        transaction.setBudget(budget);
        transactionService.createTransaction(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body("Transaction added successfully");
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<TransactionDto>> getTransactions(){
        return  ResponseEntity.status(HttpStatus.OK).body(transactionService.getTransactions());
    }
    @GetMapping("/transactions/search")
    public ResponseEntity<List<TransactionDto>> searchTransactions(
            @RequestParam(value = "searchQuery", required = false) String searchQuery,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(value = "currency", required = false) String currency,
            @RequestParam(value = "status", required = false) String status) {
        List<TransactionDto> transactions = transactionService.searchTransactions(searchQuery, startDate, endDate, currency, status);
        return ResponseEntity.ok(transactions);
    }

    @DeleteMapping("/transactions/{id}")
    public ResponseEntity<String> deleteTransactions(@PathVariable(name="id") Integer id){
        transactionService.deleteTransactions(id);
        return  ResponseEntity.status(HttpStatus.OK).body(String.format("Transaction %s has been deleted!",id));
    }
    @PutMapping("/transactions/{id}/status")
    public ResponseEntity<String> updateTransactionsStatus(@PathVariable(name = "id") Integer id) {
        try {
            Transaction transaction = transactionService.getTransactionById(id);
            Budget budget = transaction.getBudget();

            Integer budgetId = budget.getId();
            Integer budgetRemaining = budget.getRemainingAmount();
            Integer transactionAmount = transaction.getAmount();

            if (transactionAmount > budgetRemaining) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(String.format("Transaction amount of %d exceeds remaining budget of %d!", transactionAmount, budgetRemaining));
            }

            Integer remaining = budgetRemaining - transactionAmount;

            transactionService.updateTransactionsStatus(id);
            budgetService.updateBudgetAmount(budgetId, remaining);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(String.format("Transaction %s status has been updated and the remaining budget is %d!", id, remaining));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing transaction update: " + e.getMessage());
        }
    }

    @PostMapping("/budgets")
    public ResponseEntity<String> createBudget(@RequestBody @Valid AddBudgetRequest request)
    {
        Budget budget = new Budget();
        budget.setPeriodStart(request.getPeriodStart());
        budget.setPeriodEnd(request.getPeriodEnd());
        budget.setCurrency(request.getCurrency());
        budget.setApprovedAmount(request.getBudgetAmount());
        budget.setRemainingAmount(request.getBudgetAmount());
        budgetService.createBudget(budget);
        return ResponseEntity.status(HttpStatus.CREATED).body("Budget added successfully");
    }

    @GetMapping("/budgets")
    public ResponseEntity<List<BudgetDto>> getBudgets(){
        return  ResponseEntity.status(HttpStatus.OK).body(budgetService.getBudgets());
    }

    @GetMapping("/budgets/search")
    public ResponseEntity<List<BudgetDto>> searchBudgets(
            @RequestParam(value = "searchQuery", required = false) String searchQuery,
            @RequestParam(value = "year", required = false) String year) {
        List<BudgetDto> budgets = budgetService.searchBudgets(searchQuery, year);
        return ResponseEntity.ok(budgets);
    }

    @DeleteMapping("/budgets/{id}")
    public ResponseEntity<String> deleteBudgets(@PathVariable(name="id") Integer id){
        budgetService.deleteBudget(id);
        return  ResponseEntity.status(HttpStatus.OK).body(String.format("Transaction %s has been deleted!",id));
    }


}
