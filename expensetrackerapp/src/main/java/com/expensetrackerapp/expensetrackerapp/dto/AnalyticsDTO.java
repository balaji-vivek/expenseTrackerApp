package com.expensetracker.expenseTrackerApp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnalyticsDTO {
    private String mostExpensiveMonth;
    private String mostUsedCategory;
    private double averageMonthlySpend;
    private List<MonthlyExpenseTrend> expenseTrend;
}
