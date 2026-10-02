package dev.lpa;

import java.time.LocalDate;
import java.util.*;

public class ExpenseTracker{
    private ExpenseRepository repository;
    private List<Expense> expenses;

    public ExpenseTracker(ExpenseRepository repository){
        this.repository = repository;
        expenses = repository.loadExpenses();
    }

    public Expense addExpense(String category,double amount,LocalDate date) {
        Expense e = new Expense(category,amount,date);
        expenses.add(e);
        repository.saveExpenses(expenses);
        return e;
    }

    public List<Expense> getExpenses(){
        return Collections.unmodifiableList(expenses);
    }

    public boolean deleteExpense(int id) {
        for(int i = 0;i < expenses.size();i++){
            Expense e = expenses.get(i);
            if(e.getID() == id){
                expenses.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean updateExpense(int id, String newCat, double newAmount, LocalDate newDate) {

        for(int i = 0;i < expenses.size();i++){
            Expense e = expenses.get(i);
            if(e.getID() == id){
                e.setCategory(newCat);
                e.setAmount(newAmount);
                e.setDate(newDate);
                repository.saveExpenses(expenses);
                return true;
            }
        }
        return false;
    }

    public ExpenseSummary summary() {

        if (expenses.isEmpty()) {
            return null;
        }

        Map<String, Double> categoryWiseExpenses = new HashMap<>();

        double totalExpense = 0;
        double highestExpense = expenses.get(0).getAmount();

        for (Expense e : expenses) {
            totalExpense += e.getAmount();
            highestExpense = Math.max(highestExpense, e.getAmount());

            categoryWiseExpenses.put(
                    e.getCategory(),
                    categoryWiseExpenses.getOrDefault(e.getCategory(), 0.0)
                            + e.getAmount()
            );
        }

        String highestCat = "";
        double highestCatAmount = 0;

        for (var entry : categoryWiseExpenses.entrySet()) {
            if (entry.getValue() > highestCatAmount) {
                highestCatAmount = entry.getValue();
                highestCat = entry.getKey();
            }
        }

        double averageExpense = totalExpense / expenses.size();

        return new ExpenseSummary(
                totalExpense,
                expenses.size(),
                averageExpense,
                highestExpense,
                highestCat,
                categoryWiseExpenses
        );
    }

    public List<Expense> searchByCategory(String category){
        List<Expense> result = new ArrayList<>();

        for (Expense e : expenses) {
            if (e.getCategory().equals(category)) {
                result.add(e);
            }
        }

        return result;
    }

    public List<Expense> searchByDate(LocalDate date){
        List<Expense> result = new ArrayList<>();

        for (Expense e : expenses) {
            if (e.getDate().equals(date)) {
                result.add(e);
            }
        }

        return result;
    }

    public List<Expense> sortByAmount(){
        Collections.sort(expenses);
        return expenses;
    }

    public List<Expense> sortByAmountDescending(){
        expenses.sort((e1,e2) -> Double.compare(e2.getAmount(),e1.getAmount()));
        return expenses;
    }

    public List<Expense> filterByAmount(double min, double max){
        List<Expense> filteredExpenses = new ArrayList<>();

        for (Expense e : expenses) {
            if (e.getAmount() >= min && e.getAmount() <= max) {
                filteredExpenses.add(e);
            }
        }

        return filteredExpenses;
    }

}
