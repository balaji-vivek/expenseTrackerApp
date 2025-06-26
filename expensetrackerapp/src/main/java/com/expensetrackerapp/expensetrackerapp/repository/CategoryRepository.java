package com.expensetracker.expenseTrackerApp.repository;

import com.expensetracker.expenseTrackerApp.model.Category;
import com.expensetracker.expenseTrackerApp.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    @Query("SELECT e.category.name, COUNT(e) as count FROM Expense e GROUP BY e.category.name ORDER BY count DESC")
    List<Object[]> findMostUsedCategory();

}

