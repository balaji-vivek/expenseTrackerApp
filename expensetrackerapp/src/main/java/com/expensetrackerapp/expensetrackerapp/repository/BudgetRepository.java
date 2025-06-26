package com.expensetracker.expenseTrackerApp.repository;

import com.expensetracker.expenseTrackerApp.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget,Long> {

    Optional<Budget> findByCategoryIdAndMonthAndYear(Long categoryId, int month, int year);

    @Query("SELECT b.category.name, b.amount, SUM(e.amount) " +
            "FROM Budget b LEFT JOIN Expense e " +
            "ON b.category = e.category AND MONTH(e.date) = b.month AND YEAR(e.date) = b.year " +
            "GROUP BY b.category.name, b.amount")
    List<Object[]> getBudgetSummary();
}
