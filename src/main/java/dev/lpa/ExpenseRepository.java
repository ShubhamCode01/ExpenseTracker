package dev.lpa;

import java.util.List;

public interface ExpenseRepository {
    void saveExpenses(List<Expense> expenses);

    List<Expense> loadExpenses();
}
