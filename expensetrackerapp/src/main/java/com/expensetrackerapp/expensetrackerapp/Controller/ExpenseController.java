package com.expensetracker.expenseTrackerApp.Controller;

import com.expensetracker.expenseTrackerApp.dto.AnalyticsDTO;
import com.expensetracker.expenseTrackerApp.dto.ExpenseDTO;
import com.expensetracker.expenseTrackerApp.dto.MonthlySummaryDTO;
import com.expensetracker.expenseTrackerApp.model.Expense;
import com.expensetracker.expenseTrackerApp.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/expenses")
@Valid
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/getallExpenses")
    public List<Expense> getAll() {
        return expenseService.getAllExpenses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpenseById(Long id) {
        return expenseService
                .getExpenseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createExpense(@RequestBody @Valid ExpenseDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        return ResponseEntity.ok(expenseService.createExpense(dto));
    }


    public ResponseEntity<Expense> update(@PathVariable Long id, @RequestBody Expense expense) {
        return expenseService
                .updateExpense(id, expense)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/deleteById/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        boolean deleted = expenseService.deleteExpense(id);
        Map<String, String> response = new HashMap<>();

        if (deleted) {
            response.put("message", "Expense with ID " + id + " deleted successfully.");
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "Expense with ID " + id + " not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/summary")
    public ResponseEntity<MonthlySummaryDTO> getMonthlySummary(
            @RequestParam int month,
            @RequestParam int year) {
        return ResponseEntity.ok(expenseService.getMonthlySummary(month, year));
    }
    @GetMapping("/analytics")
    public ResponseEntity<AnalyticsDTO> getAnalytics() {
        return ResponseEntity.ok(expenseService.getAnalytics());
    }

    @GetMapping("/getRecurringExpense")
    public ResponseEntity<List<Expense>> generateRecurringExpenses() {
        return ResponseEntity.ok(expenseService.generateRecurringExpenses());
    }

}
