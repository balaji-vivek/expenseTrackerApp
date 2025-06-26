package com.expensetracker.expenseTrackerApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlySummaryDTO {

    private Double totalIncome;
    private Double totalExpense;
    private Double balance;
    private Map<String,Double> topCategories;
}
