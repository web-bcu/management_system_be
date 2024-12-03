package com.csbu.finance.Service;

import com.csbu.finance.Repository.BudgetRepository;
import com.csbu.finance.dto.BudgetDto;
import com.csbu.finance.model.Budget;
import com.csbu.finance.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BudgetService {
    @Autowired
    private BudgetRepository budgetRepository;

    public void createBudget(Budget budget){
        try {
            budgetRepository.save(budget);
        } catch (Exception e) {
            throw new TransactionException("Error saving budget", e);
        }
    }

    public List<BudgetDto> getBudgets(){
        try {
            return budgetRepository.findAll()
                    .stream()
                    .map(budget -> new BudgetDto(
                            budget.getId(),
                            budget.getPeriodStart(),
                            budget.getPeriodEnd(),
                            budget.getApprovedAmount(),
                            budget.getRemainingAmount(),
                            budget.getCurrency(),
                            budget.getCreatedAt()
                    )).collect(Collectors.toList());
        }catch (Exception e){
            throw new TransactionException("Error getting budget", e);
        }
    }

    public List<BudgetDto> searchBudgets(
            String searchQuery
            , String year
    ){
        try {
            return budgetRepository.findAll()
                    .stream()
                    .filter(budget -> searchQuery == null ||
                            budget.getId().toString().equals(searchQuery)
                    ).filter(
                            budget -> year == null ||
                                    (
                                            Integer.parseInt(year)  >= budget.getPeriodStart().getYear() &&
                                                    Integer.parseInt(year)  <= budget.getPeriodEnd().getYear()
                                    )
                    )
                    .map(budget -> new BudgetDto(
                            budget.getId(),
                            budget.getPeriodStart(),
                            budget.getPeriodEnd(),
                            budget.getApprovedAmount(),
                            budget.getRemainingAmount(),
                            budget.getCurrency(),
                            budget.getCreatedAt()
                    )).collect(Collectors.toList());
        }catch (Exception e){
            throw new TransactionException("Error getting budget", e);
        }
    }

    public void deleteBudget(Integer id){
        try {
            budgetRepository.deleteById(id);
        }catch (Exception e){
            throw new TransactionException("Error deleting budget", e);
        }
    }

    public void updateBudgetAmount(Integer id, Integer amount){
        try {
            budgetRepository.updateBudgetAmount(id, amount);
        } catch (Exception e) {
            throw new TransactionException("Error updating budget", e);
        }
    }
    public Budget getBudgetById(Integer id){
        try {
            return budgetRepository.findById(id)
                    .orElseThrow(() -> new TransactionException("Budget not found with id: " + id));
        } catch (Exception e) {
            throw new TransactionException("Error getting Budget", e);
        }
    }

}

