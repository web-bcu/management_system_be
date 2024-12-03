package com.csbu.finance.Service;

import com.csbu.finance.Repository.TransactionRepository;
import com.csbu.finance.dto.TransactionDto;
import com.csbu.finance.model.Transaction;
import jakarta.transaction.Transactional;
import org.hibernate.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    public void createTransaction(Transaction transaction){
        try {
            transactionRepository.save(transaction);
        } catch (Exception e) {
            throw new TransactionException("Error saving transaction", e);
        }
    }

    public List<TransactionDto> getTransactions(){
        try {
            return transactionRepository.findAll().
                    stream()
                    .map(transaction -> new TransactionDto(
                            transaction.getId(),
                            transaction.getTransactionType(),
                            transaction.getAmount(),
                            transaction.getCurrency(),
                            transaction.getStatus(),
                            transaction.getTransactionDate(),
                            transaction.getDescription()
                            ,transaction.getBudget().getId()
                    )).collect(Collectors.toList());
        } catch (Exception e) {
            throw new TransactionException("Error getting transaction", e);
        }
    }

    public  List<TransactionDto>  searchTransactions(
            String searchQuery,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            String currency,
            String status
    ){
        try {
            return transactionRepository.findAll().stream()
                    .filter(transaction -> searchQuery == null ||
                            transaction.getDescription().toLowerCase().contains(searchQuery.toLowerCase()) ||
                            transaction.getTransactionType().toLowerCase().contains(searchQuery.toLowerCase()) ||
                            transaction.getId().toString().equals(searchQuery)
                    )
                    .filter(transaction -> startDate == null || !transaction.getTransactionDate().toLocalDate().isBefore(startDate))
                    .filter(transaction -> endDate == null || !transaction.getTransactionDate().toLocalDate().isAfter(endDate))
                    .filter(transaction -> currency == null || transaction.getCurrency().equals(currency))
                    .filter(transaction -> status == null || transaction.getStatus().equals(status))
                    .map(transaction -> new TransactionDto(
                            transaction.getId(),
                            transaction.getTransactionType(),
                            transaction.getAmount(),
                            transaction.getCurrency(),
                            transaction.getStatus(),
                            transaction.getTransactionDate(),
                            transaction.getDescription()
                            ,transaction.getBudget().getId()
                    )).collect(Collectors.toList());
        }catch (Exception e){
            throw new TransactionException("Error getting transaction", e);
        }
    }
    public void deleteTransactions(Integer id){
        try {
            transactionRepository.deleteById(id);
        } catch (Exception e) {
            throw new TransactionException("Error deleting transaction", e);
        }
    }

    public void updateTransactionsStatus(Integer id){
        try {
            transactionRepository.updateTransactionStatus(id);
        } catch (Exception e) {
            throw new TransactionException("Error updating transaction", e);
        }
    }
    public Transaction getTransactionById(Integer id){
        try {
            return transactionRepository.findById(id)
                    .orElseThrow(() -> new TransactionException("Transaction not found with id: " + id));
        } catch (Exception e) {
            throw new TransactionException("Error getting transaction", e);
        }
    }

}
