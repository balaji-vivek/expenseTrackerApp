package com.expensetracker.expenseTrackerApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyExpenseTrend {
    private int year;
    private int month;
    private double total;
}
