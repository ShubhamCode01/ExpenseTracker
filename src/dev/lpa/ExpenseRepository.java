package dev.lpa;

import java.util.ArrayList;

public interface ExpenseRepository {
    void saveExpenses(ArrayList<Expense> expenses);

    ArrayList<Expense> loadExpenses();
}
