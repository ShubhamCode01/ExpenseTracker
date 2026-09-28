package dev.lpa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ExpenseTracker{
    private ExpenseRepository repository;
    private ArrayList<Expense>expenses;

    public ExpenseTracker(ExpenseRepository repository){
        this.repository = repository;
        expenses = repository.loadExpenses();
    }

    public void addExpense(String category,double amount,String date) {
        Expense e = new Expense(category,amount,date);
        expenses.add(e);
        System.out.println("Expense added with ID " + e.getID());
        repository.saveExpenses(expenses);
    }

    public void viewExpenses(){
        if(expenses.isEmpty()){
            System.out.println("No Expenses found");
        }else{
            for (Expense e : expenses) {
                e.print();
            }
        }
    }

    public void deleteExpense(int id) {
        boolean found = false;
        for(int i = 0;i < expenses.size();i++){
            Expense e = expenses.get(i);
            if(e.getID() == id){
                expenses.remove(i);
                found = true;
                break;
            }
        }
        if(found){
            System.out.println("Expense with id " + id + "Deleted Successfully.");
            repository.saveExpenses(expenses);
        }
        else
            System.out.println("Expense not found");

    }

    public boolean isPresent(int id){
        for(int i = 0;i < expenses.size();i++){
            Expense e = expenses.get(i);
            if(e.getID() == id){
               return true;
            }
        }
        return false;
    }

    public void updateExpense(int id,String newCat,double newAmount,String newDate) {

        for(int i = 0;i < expenses.size();i++){
            Expense e = expenses.get(i);
            if(e.getID() == id){
                e.setCategory(newCat);
                e.setAmount(newAmount);
                e.setDate(newDate);
                System.out.println("Expense updated successfully.");
                repository.saveExpenses(expenses);
                break;
            }
        }
    }

    public void summary(){
        if(expenses.isEmpty()){
            System.out.println("No Expenses Found.");
            return;
        }
        HashMap<String,Double> categoryWiseExpenses = new HashMap<>();
        double totalExpense = 0;
        Expense x = expenses.get(0);
        double highestExpense = x.getAmount();
        for(Expense e : expenses){
            totalExpense += e.getAmount();
            highestExpense = Math.max(highestExpense,e.getAmount());
            categoryWiseExpenses.put(e.getCategory(),categoryWiseExpenses.getOrDefault(e.getCategory(),0.0) + e.getAmount());
        }
        String highestCat = "";
        double highestCatAmount = 0;
        for(var y : categoryWiseExpenses.entrySet()){
            if(y.getValue() > highestCatAmount){
                highestCatAmount = y.getValue();
                highestCat = y.getKey();
            }
        }
        System.out.println("===Expense Summary===");
        System.out.println("Total Expense : " + totalExpense);
        System.out.println("Number of Expenses : " + expenses.size());
        System.out.println("Average Expense : " + (totalExpense) / expenses.size());
        System.out.println("Highest Expense : " + highestExpense);
        System.out.println("Highest Expense category : " + highestCat);
    }

    public void searchByCategory(String category){
        boolean flag = false;
        for(Expense e : expenses){
            if(e.getCategory().equalsIgnoreCase(category)){
                flag = true;
                System.out.println("Category : " + e.getCategory() + " Amount : " + e.getAmount() + " Date : " + e.getDate());
            }
        }
        if(!flag){
            System.out.println("Category not found.");
        }
    }

    public void searchByDate(String date){
        boolean flag = false;
        for(Expense e : expenses){
            if(e.getDate().equalsIgnoreCase(date)){
                flag = true;
                System.out.println("Category : " + e.getCategory() + " Amount : " + e.getAmount() + " Date : " + e.getDate());
            }
        }
        if(!flag){
            System.out.println("Date not found.");
        }
    }

    public void sortByAmount(){
        if(expenses.isEmpty()){
            System.out.println("No expenses to found");
            return;
        }
        Collections.sort(expenses);
        viewExpenses();
    }

    public void sortByAmountDescending(){
        if(expenses.isEmpty()){
            System.out.println("No expenses to found");
            return;
        }
        expenses.sort((e1,e2) -> Double.compare(e2.getAmount(),e1.getAmount()));
        viewExpenses();
    }

    public void filterByAmount(double min,double max){
        boolean flag = false;
        for(Expense e : expenses){
            if(e.getAmount() <= max && e.getAmount() >= min){
                System.out.println(e.getCategory() + " " + e.getAmount());
                flag = true;
            }
        }
        if(flag == false){
            System.out.println("No expenses found in the range.");
        }
    }

}
