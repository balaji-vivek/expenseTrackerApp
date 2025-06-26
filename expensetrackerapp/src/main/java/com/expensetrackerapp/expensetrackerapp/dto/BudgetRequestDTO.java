package com.expensetracker.expenseTrackerApp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BudgetRequestDTO {

    private Long categoryId;
    private int month;
    private int year;
    private double amount;
}
