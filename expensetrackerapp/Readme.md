# 💸 Expense Tracker Application

A full-featured **personal finance backend** built with **Spring Boot** and **PostgreSQL**. This app helps users manage expenses, income, categories, recurring transactions, budgets, and analytics with a RESTful API.

---

## 📦 Tech Stack

- **Java 17**
- **Spring Boot 3.5.3**
- **Spring Data JPA**
- **PostgreSQL**
- **Lombok**
- **Swagger (OpenAPI)**
- **Mockito + JUnit**
- **Scheduled Tasks (@Scheduled)**

---

## 🚀 Features Implemented

### ✅ Expense Management
- `POST /api/expenses/create` – Create a new expense
- `GET /api/expenses/all` – Fetch all expenses
- `DELETE /api/expenses/deleteById/{id}` – Delete expense by ID
- Validations with `@NotNull`, `@NotBlank`, `@DecimalMin`

### ✅ Category Management
- `POST /api/categories/create` – Create new category (income/expense)
- `GET /api/categories/all` – List all categories
- Linked with expense via `@ManyToOne`

### ✅ Summary Endpoint
- `GET /api/expenses/summary?month=6&year=2025`
  - Total Income
  - Total Expense
  - Balance
  - Top Categories (grouped)

### ✅ Recurring Expenses/Income
- `recurring: true/false`
- `recurrenceType: MONTHLY | WEEKLY`
- Auto-generation of next entries via:
  - `@Scheduled` task at 2 AM daily
  - Manual trigger: `GET /api/expenses/generateRecurring`

### ✅ Insight & Analytics
- `GET /api/analytics`
  - 📅 Most Expensive Month
  - 🏷 Most Used Category
  - 📊 Average Monthly Spend
  - 📈 Monthly Expense Trend (for graphs)

### ✅ Budget Planning Module
- `POST /api/budgets` – Set monthly budget per category
- `GET /api/budgets/summary`
  - Budget vs Spent for each category/month

---

## 📘 Example JSON for Creating Expense

```json
{
  "title": "Internet Bill",
  "amount": 999.0,
  "categoryId": 1,
  "date": "2025-06-24",
  "recurring": true,
  "recurrenceType": "MONTHLY"
}
