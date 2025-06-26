package com.expensetracker.expenseTrackerApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Expense {
    public enum RecurrenceType {
        MONTHLY,
        WEEKLY
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private double amount;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;


    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private RecurrenceType recurrenceType;

    private boolean recurring;
}
