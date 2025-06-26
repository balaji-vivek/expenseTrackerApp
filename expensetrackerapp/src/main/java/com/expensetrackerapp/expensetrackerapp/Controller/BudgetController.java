package com.expensetracker.expenseTrackerApp.Controller;

import com.expensetracker.expenseTrackerApp.dto.BudgetRequestDTO;
import com.expensetracker.expenseTrackerApp.dto.BudgetSummaryDTO;
import com.expensetracker.expenseTrackerApp.model.Budget;
import com.expensetracker.expenseTrackerApp.service.BudgetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    @PostMapping("/createBudget")
    public ResponseEntity<Budget> createBudget(@RequestBody @Valid BudgetRequestDTO dto){
        return ResponseEntity.ok(budgetService.createBudget(dto));
    }

    @GetMapping("/summary")
    public ResponseEntity<List<BudgetSummaryDTO>> getSummary() {
        return ResponseEntity.ok(budgetService.getBudgetSummary());
    }
}
