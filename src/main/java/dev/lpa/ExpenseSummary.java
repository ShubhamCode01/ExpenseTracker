package dev.lpa;

import java.util.Map;

public class ExpenseSummary {
    private double totalExpense;
    private int numberOfExpenses;
    private double averageExpense;
    private double highestExpense;
    private String highestExpenseCategory;
    private Map<String, Double> categoryBreakdown;

    public ExpenseSummary(double totalExpense,
                          int numberOfExpenses,
                          double averageExpense,
                          double highestExpense,
                          String highestExpenseCategory,
                          Map<String, Double> categoryBreakdown) {

        this.totalExpense = totalExpense;
        this.numberOfExpenses = numberOfExpenses;
        this.averageExpense = averageExpense;
        this.highestExpense = highestExpense;
        this.highestExpenseCategory = highestExpenseCategory;
        this.categoryBreakdown = categoryBreakdown;
    }

    public double getTotalExpense() {
        return totalExpense;
    }

    public int getNumberOfExpenses() {
        return numberOfExpenses;
    }

    public double getAverageExpense() {
        return averageExpense;
    }

    public double getHighestExpense() {
        return highestExpense;
    }

    public String getHighestExpenseCategory() {
        return highestExpenseCategory;
    }

    public Map<String, Double> getCategoryBreakdown() {
        return categoryBreakdown;
    }
}
