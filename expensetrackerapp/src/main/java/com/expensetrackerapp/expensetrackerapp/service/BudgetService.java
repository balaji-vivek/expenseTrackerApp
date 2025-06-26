package com.expensetracker.expenseTrackerApp.service;

import com.expensetracker.expenseTrackerApp.dto.BudgetRequestDTO;
import com.expensetracker.expenseTrackerApp.dto.BudgetSummaryDTO;
import com.expensetracker.expenseTrackerApp.model.Budget;
import com.expensetracker.expenseTrackerApp.model.Category;
import com.expensetracker.expenseTrackerApp.repository.BudgetRepository;
import com.expensetracker.expenseTrackerApp.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;

    public BudgetService(BudgetRepository budgetRepository,CategoryRepository categoryRepository){
        this.budgetRepository=budgetRepository;
        this.categoryRepository=categoryRepository;
    }

    public Budget createBudget(BudgetRequestDTO dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Budget budget = Budget.builder()
                .category(category)
                .month(dto.getMonth())
                .year(dto.getYear())
                .amount(dto.getAmount())
                .build();
        return budgetRepository.save(budget);
    }
    public List<BudgetSummaryDTO> getBudgetSummary() {
        List<Object[]> results = budgetRepository.getBudgetSummary();
        return results.stream().map(obj -> new BudgetSummaryDTO(
                (String) obj[0],
                (Double) obj[1],
                obj[2] == null ? 0.0 : ((Double) obj[2])
        )).collect(Collectors.toList());
    }
}
