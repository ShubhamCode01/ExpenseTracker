package dev.lpa;

public class Expense implements Comparable<Expense>{
    private String category;
    private double amount;
    private String date;
    private int id;
    private static int nextID = 0;

    public Expense(String category,double amount,String date){
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.id = ++nextID;
    }

    public Expense(int id,String category,double amount,String date){
        this.id = id;
        this.category = category;
        this.amount = amount;
        this.date = date;
        nextID = id;
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

    public String getDate(){
        return date;
    }
    public void print(){
        System.out.println( "ID : "+ this.id + " Expense Category : " + this.category + " |Expense Amount : " + this.amount + " |Date : " + this.date);
    }

    public void setId(int id){
        this.id = id;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public void setAmount(double amount){
        this.amount = amount;
    }

    public void setDate(String date){
        this.date = date;
    }


    @Override
    public int compareTo(Expense o) {
        return Double.compare(this.amount,o.amount);
    }
}
