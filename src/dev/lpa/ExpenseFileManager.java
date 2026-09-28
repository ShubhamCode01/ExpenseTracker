package dev.lpa;

import java.io.*;
import java.util.ArrayList;

public class ExpenseFileManager implements ExpenseRepository{

    @Override
    public void saveExpenses(ArrayList<Expense> expenses){
        try(PrintWriter pw = new PrintWriter("expenses.text");){
            for(Expense e : expenses){
                pw.print(e.getID() + "|");
                pw.print(e.getCategory() + "|");
                pw.print(e.getAmount() + "|");
                pw.println(e.getDate());
            }
        }catch(FileNotFoundException x){
            System.out.println("Error writing file.");
        }
    }

    @Override
    public ArrayList<Expense> loadExpenses(){
        ArrayList<Expense> expenses =  new ArrayList<>();
        File file = new File("expenses.text");

        if (!file.exists()) {
            return expenses;
        }

        try(BufferedReader br = new BufferedReader(new FileReader(file));){
            String line;
            while((line = br.readLine()) != null){
                String[] parts = line.split("\\|");
                int id = Integer.parseInt(parts[0]);
                String cat = parts[1];
                double amt = Double.parseDouble(parts[2]);
                String date = parts[3];
                Expense e = new Expense(id,cat,amt,date);
                expenses.add(e);
            }
        }catch(IOException x){
            System.out.println("Error loading file");
        }
        return expenses;
    }
}
