package com.expensetracker.expenseTrackerApp.dto;

import com.expensetracker.expenseTrackerApp.model.Category;
import com.expensetracker.expenseTrackerApp.model.Expense;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;


import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseDTO {

    @NotBlank(message = "Title is required")
    private String title;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0",inclusive =false,message = "Amount must greater than 0")
    private Double amount;

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    @NotNull(message = "Date is required")
    private LocalDate date;

    private Boolean recurring;
    private Expense.RecurrenceType recurrenceType;

}
