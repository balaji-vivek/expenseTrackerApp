package com.expensetracker.expenseTrackerApp.service;

import com.expensetracker.expenseTrackerApp.dto.AnalyticsDTO;
import com.expensetracker.expenseTrackerApp.dto.ExpenseDTO;
import com.expensetracker.expenseTrackerApp.dto.MonthlyExpenseTrend;
import com.expensetracker.expenseTrackerApp.dto.MonthlySummaryDTO;
import com.expensetracker.expenseTrackerApp.model.Category;
import com.expensetracker.expenseTrackerApp.model.Expense;
import com.expensetracker.expenseTrackerApp.repository.CategoryRepository;
import com.expensetracker.expenseTrackerApp.repository.ExpenseRepository;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

    private final ExpenseRepository repository;
    private final CategoryRepository categoryRepository;

    public ExpenseService(ExpenseRepository repository,CategoryRepository categoryRepository) {
        this.repository = repository;
        this.categoryRepository=categoryRepository;
    }

    public List<Expense> getAllExpenses() {
        return repository.findAll();
    }

    public Optional<Expense> getExpenseById(Long id) {
        return repository.findById(id);
    }

    public Expense createExpense(ExpenseDTO dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        Expense expense = Expense.builder()
                .title(dto.getTitle())
                .amount(dto.getAmount())
                .date(dto.getDate())
                .category(category)
                .recurring(Boolean.TRUE.equals(dto.getRecurring()))
                .recurrenceType(dto.getRecurrenceType())
                .build();

        return repository.save(expense);
    }

    public Optional<Expense> updateExpense(Long id, Expense updatedExpense) {
        return repository.findById(id).map(expense -> {
            expense.setTitle(updatedExpense.getTitle());
            expense.setAmount(updatedExpense.getAmount());
            expense.setCategory(updatedExpense.getCategory());
            expense.setDate(updatedExpense.getDate());
            return repository.save(expense);
        });
    }
    public boolean deleteExpense(Long id) {
        Optional<Expense> expenseOpt = repository.findById(id);
        if (expenseOpt.isPresent()) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
    public MonthlySummaryDTO getMonthlySummary(int month, int year) {
        List<Expense> monthlyExpenses = repository.findByMonthAndYear(month, year);

        double totalIncome = monthlyExpenses.stream().filter(e -> e.getCategory()
                        .getType().equalsIgnoreCase("income"))
                .mapToDouble(Expense::getAmount).sum();

        double totalExpense = monthlyExpenses.stream().filter(e -> e.getCategory()
                        .getType().equalsIgnoreCase("expense"))
                .mapToDouble(Expense::getAmount).sum();

        Map<String, Double> topCategories = monthlyExpenses.stream()
                .collect(Collectors.groupingBy(e -> e.getCategory().getName(),
                        Collectors.summingDouble(Expense::getAmount)));

        return new MonthlySummaryDTO(totalIncome, totalExpense, totalIncome - totalExpense, topCategories);
    }
    public String getMostExpensiveMonth() {
        List<Object[]> results = repository.findMostExpensiveMonth();

        if (results == null || results.isEmpty()) {
            return "No expenses found";
        }

        Object[] result = results.get(0);

        int month = ((Number) result[0]).intValue();
        int year = ((Number) result[1]).intValue();

        String monthName = Month.of(month).getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        return monthName + " " + year;
    }

    public AnalyticsDTO getAnalytics() {
        AnalyticsDTO dto = new AnalyticsDTO();
        dto.setMostExpensiveMonth(getMostExpensiveMonth());
        dto.setMostUsedCategory(getMostUsedCategory());
        dto.setAverageMonthlySpend(getAverageMonthlySpend());
        dto.setExpenseTrend(getMonthlyTrend());
        return dto;
    }

    public List<MonthlyExpenseTrend> getMonthlyTrend() {
        List<Object[]> results = repository.findMostExpensiveMonth();
        return results.stream().map(obj -> new MonthlyExpenseTrend(
                ((Number) obj[1]).intValue(),
                ((Number) obj[0]).intValue(), 
                ((Number) obj[2]).doubleValue()
        )).collect(Collectors.toList());
    }
    public String getMostUsedCategory() {
        List<Object[]> results = repository.findMostUsedCategory();
        if (results.isEmpty()) return null;
        return (String) results.get(0)[0];
    }
    public double getAverageMonthlySpend() {
        List<MonthlyExpenseTrend> trends = getMonthlyTrend();
        if (trends.isEmpty()) return 0.0;

        double total = trends.stream().mapToDouble(MonthlyExpenseTrend::getTotal).sum();
        return total / trends.size();
    }

    public List<Expense> generateRecurringExpenses() {
        List<Expense> recurringExpenses = repository.findAllByRecurringTrue();
        List<Expense> newEntries = new ArrayList<>();

        for (Expense e : recurringExpenses) {
            if (shouldGenerate(e)) {
                Expense next = new Expense();
                next.setTitle(e.getTitle());
                next.setAmount(e.getAmount());
                next.setCategory(e.getCategory());
                next.setDate(calculateNextDate(e.getDate(), e.getRecurrenceType()));
                next.setRecurrenceType(e.getRecurrenceType());
                next.setRecurring(false);

                newEntries.add(repository.save(next));
            }
        }
        return newEntries;
    }


    @Scheduled(cron = "0 0 2 * * *") // Every day at 2 AM
    public void autoGenerateRecurringExpenses() {
        generateRecurringExpenses();
    }

    private LocalDate calculateNextDate(LocalDate currrent, Expense.RecurrenceType type){
        return type == Expense.RecurrenceType.MONTHLY?currrent.plusMonths(1):currrent.plusWeeks(1);
    }

    private boolean shouldGenerate(Expense e){
        LocalDate lastDate = e.getDate();
        LocalDate today = LocalDate.now();

        if(e.getRecurrenceType() == Expense.RecurrenceType.MONTHLY){
            return ChronoUnit.MONTHS.between(lastDate,today)>=1;
        } else if(e.getRecurrenceType() == Expense.RecurrenceType.WEEKLY){
            return ChronoUnit.WEEKS.between(lastDate,today)>=1;
        }
        return false;
    }

}
