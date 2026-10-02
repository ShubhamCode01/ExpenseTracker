package dev.lpa;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ExpenseRepository repository = new ExpenseFileManager();
        ExpenseTracker tracker = new ExpenseTracker(repository);
        Scanner sc = new Scanner(System.in);
        boolean flag = true;

        while(flag) {
            System.out.println("====Expense Tracker====");
            System.out.print("1.Add Expense\n2.View Expense\n3.Delete Expense\n4.Update Expense\n5.View Summary\n6.Search by category\n7.Search by date\n8.Sort By amount\n9.Sort by amount in descending order\n10.Filter By amount\n11.Exit\n");
            int choice = readInt(sc);
            sc.nextLine();
            switch (choice) {

                case 1:
                    String category = readCategory(sc);
                    double amount = readAmount(sc);
                    sc.nextLine();
                    LocalDate date = readDate(sc);
                    Expense expense = tracker.addExpense(category, amount, date);
                    System.out.println("Expense added with ID " + expense.getID());
                    break;

                case 2:
                    List<Expense> allExpenses = tracker.getExpenses();
                    if(allExpenses.isEmpty()){
                        System.out.println("No Expenses found");
                    } else {
                        for (Expense e : allExpenses) {
                            System.out.println(e);
                        }
                    }
                    break;

                case 3:
                    int id = readID(sc);
                    if (tracker.deleteExpense(id)) {
                        System.out.println("Expense with id " + id + " deleted successfully.");
                    } else {
                        System.out.println("Expense not found.");
                    }
                    break;

                case 4:
                    id = readID(sc);
                    sc.nextLine();

                    String newCat = readCategory(sc);
                    double newAmount = readAmount(sc);
                    sc.nextLine();
                    LocalDate newDate = readDate(sc);

                    if (tracker.updateExpense(id, newCat, newAmount, newDate)) {
                        System.out.println("Expense updated successfully.");
                    } else {
                        System.out.println("Expense not found.");
                    }
                    break;

                case 5:
                    ExpenseSummary summary = tracker.summary();

                    if (summary == null) {
                        System.out.println("No Expenses Found.");
                        break;
                    }

                    System.out.println("===Expense Summary===");
                    System.out.println("Total Expense : " + summary.getTotalExpense());
                    System.out.println("Number of Expenses : " + summary.getNumberOfExpenses());
                    System.out.println("Average Expense : " + summary.getAverageExpense());
                    System.out.println("Highest Expense : " + summary.getHighestExpense());
                    System.out.println("Highest Expense category : " + summary.getHighestExpenseCategory());

                    System.out.println("Category Breakdown:");

                    for (var entry : summary.getCategoryBreakdown().entrySet()) {
                        System.out.printf(
                                "%s : %.2f (%.2f%%)%n",
                                entry.getKey(),
                                entry.getValue(),
                                (entry.getValue() / summary.getTotalExpense()) * 100
                        );
                    }
                    break;

                case 6:
                    String searchCategory = readCategory(sc);
                    List<Expense> categoryExpenses = tracker.searchByCategory(searchCategory);

                    if (categoryExpenses.isEmpty()) {
                        System.out.println("Category not found.");
                    } else {
                        for (Expense e : categoryExpenses) {
                            System.out.println(e);
                        }
                    }
                    break;

                case 7:
                    LocalDate searchDate = readDate(sc);
                    List<Expense> dateExpenses = tracker.searchByDate(searchDate);

                    if (dateExpenses.isEmpty()) {
                        System.out.println("Date not found.");
                    } else {
                        for (Expense e : dateExpenses) {
                            System.out.println(e);
                        }
                    }
                    break;

                case 8:
                    for (Expense e : tracker.sortByAmount()) {
                        System.out.println(e);
                    }
                    break;

                case 9:
                    for (Expense e : tracker.sortByAmountDescending()) {
                        System.out.println(e);
                    }
                    break;

                case 10:
                    while (true) {
                        double min = readAmount(sc);
                        double max = readAmount(sc);

                        if (max >= min) {
                            List<Expense> filteredExpenses = tracker.filterByAmount(min, max);

                            if (filteredExpenses.isEmpty()) {
                                System.out.println("No expenses found in the range.");
                            } else {
                                for (Expense e : filteredExpenses) {
                                    System.out.println(e);
                                }
                            }
                            break;
                        }

                        System.out.println("Invalid search range.");
                    }
                    break;

                case 11:
                    flag = false;
                    break;

                default:
                    System.out.println("Invalid Choice , Enter Correct Option.");
            }
        }
    }

    private static LocalDate readDate(Scanner sc){
        while(true){
            System.out.print("Enter date (yyyy-mm-dd) : ");
            String input = sc.nextLine();
            try{
                return LocalDate.parse(input);
            }catch (DateTimeException e){
                System.out.println("Invalid date. Please use yyyy-MM-dd.");
            }
        }
    }

    private static double readAmount(Scanner sc){
        while(true){
            System.out.print("Enter Amount : ");

            try{
                double amount = sc.nextDouble();
                if(amount > 0){
                    return amount;
                }
                System.out.println("Amount must be greater than 0.");
            }catch (InputMismatchException e){
                System.out.println("Invalid amount.Please enter a valid positive number.");
                sc.nextLine();
            }
        }
    }

    private static int readInt(Scanner sc){
        while(true){
            System.out.println("Enter choice : ");
            try{
                int choice = sc.nextInt();
                if(choice < 1 || choice > 11){
                    System.out.println("Enter choice between 1-11");
                    continue;
                }
                return choice;
            }catch(InputMismatchException e){
                System.out.println("Invalid input.please enter a valid integer.");
                sc.nextLine();
            }
        }
    }

    private static int readID(Scanner sc){
        while(true){
            System.out.print("Enter ID : ");
            try{
                int id = sc.nextInt();
                if(id < 1){
                    System.out.println("ID must be greater than 0.");
                    continue;
                }
                return id;
            }catch(InputMismatchException e){
                System.out.println("Invalid ID.please enter a valid integer.");
                sc.nextLine();
            }
        }
    }

    private static String readCategory(Scanner sc){
        while(true){
            System.out.print("Enter category : ");
            String category = sc.nextLine();
            category = category.trim().toLowerCase();
            if(category.isEmpty()){
                System.out.println("Category cannot be empty.");
                    continue;
            }
            category = category.toUpperCase().charAt(0) + category.substring(1);
            return category;
        }
    }
}

