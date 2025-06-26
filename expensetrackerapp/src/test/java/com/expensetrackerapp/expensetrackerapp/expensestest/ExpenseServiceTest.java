package com.expensetracker.expenseTrackerApp.expensestest;

import com.expensetracker.expenseTrackerApp.dto.ExpenseDTO;
import com.expensetracker.expenseTrackerApp.model.Category;
import com.expensetracker.expenseTrackerApp.model.Expense;
import com.expensetracker.expenseTrackerApp.repository.CategoryRepository;
import com.expensetracker.expenseTrackerApp.repository.ExpenseRepository;
import com.expensetracker.expenseTrackerApp.service.ExpenseService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ExpenseServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ExpenseService expenseService;

    public ExpenseServiceTest(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateExpense_Success(){
        ExpenseDTO dto = ExpenseDTO.builder()
                .title("Food")
                .amount(100.0)
                .categoryId(1L)
                .date(LocalDate.of(2025,6,25)).build();

        Category mockCategory = Category
                .builder().id(1L)
                .name("Food")
                .type("expense")
                .build();
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(mockCategory));
        Expense savedExpense = Expense.builder()
                .id(1L).title("Food")
                .amount(100.0).category(mockCategory)
                .date(LocalDate.of(2025,6,24)).build();
        when(expenseRepository.save(any(Expense.class))).thenReturn(savedExpense);

        Expense result = expenseService.createExpense(dto);
        assertNotNull(result);
        assertEquals("Food",result.getTitle());
        assertEquals(100,result.getAmount());
        assertEquals("Food",result.getCategory().getName());

        verify(categoryRepository,times(1)).findById(1L);
        verify(expenseRepository,times(1)).save(any(Expense.class));
    }
}
