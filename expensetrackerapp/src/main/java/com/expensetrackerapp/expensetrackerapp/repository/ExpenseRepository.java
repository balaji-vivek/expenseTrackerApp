package com.expensetracker.expenseTrackerApp.repository;

import com.expensetracker.expenseTrackerApp.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense,Long> {
    @Query("SELECT e FROM Expense e WHERE MONTH(e.date) = :month AND YEAR(e.date) = :year")
    List<Expense> findByMonthAndYear(@Param("month") int month, @Param("year") int year);
    @Query(value = "SELECT EXTRACT(MONTH FROM e.date) AS month, EXTRACT(YEAR FROM e.date) AS year, SUM(e.amount) as total " +
            "FROM expense e GROUP BY year, month ORDER BY total DESC", nativeQuery = true)
    List<Object[]> findMostExpensiveMonth();
    @Query("SELECT AVG(monthly_total) FROM (SELECT SUM(e.amount) as monthly_total " +
            "FROM Expense e GROUP BY FUNCTION('YEAR', e.date), FUNCTION('MONTH', e.date))")
    Double findAverageMonthlySpend();
    @Query("SELECT FUNCTION('YEAR', e.date) as year, FUNCTION('MONTH', e.date) as month, SUM(e.amount) as total " +
            "FROM Expense e GROUP BY year, month ORDER BY year, month")
    List<Object[]> findMonthlyExpenseTrend();
    @Query("SELECT e.category.name, COUNT(e) as count FROM Expense e GROUP BY e.category.name ORDER BY count DESC")
    List<Object[]> findMostUsedCategory();
    List<Expense> findAllByRecurringTrue();
}

