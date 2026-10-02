package dev.lpa;

import java.time.LocalDate;

public class Expense implements Comparable<Expense>{
    private String category;
    private double amount;
    private LocalDate date;
    private int id;
    private static int nextID = 0;

    public Expense(String category,double amount,LocalDate date){
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.id = ++nextID;
    }

    public Expense(int id,String category,double amount,LocalDate date){
        this.id = id;
        this.category = category;
        this.amount = amount;
        this.date = date;
        nextID = Math.max(nextID, id);
    }

    public int getID(){
        return this.id;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount(){
        return  amount;
    }

    public LocalDate getDate(){
        return date;
    }
    @Override
    public String toString() {
        return "ID : " + id +
                " Expense Category : " + category +
                " | Expense Amount : " + amount +
                " | Date : " + date;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public void setAmount(double amount){
        this.amount = amount;
    }

    public void setDate(LocalDate date){
        this.date = date;
    }


    @Override
    public int compareTo(Expense o) {
        return Double.compare(this.amount,o.amount);
    }
}
