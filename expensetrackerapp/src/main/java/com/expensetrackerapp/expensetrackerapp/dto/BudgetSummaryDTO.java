package com.expensetracker.expenseTrackerApp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BudgetSummaryDTO {

    private String category;
    private double budgetAmount;
    private double spentAmount;
}
