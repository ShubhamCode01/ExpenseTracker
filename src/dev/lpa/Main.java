package dev.lpa;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        ExpenseTracker tracker = new ExpenseTracker(new ExpenseFileManager());
        Scanner sc = new Scanner(System.in);
        boolean flag = true;

        while(flag) {
            System.out.println("====Expense Tracker====");
            System.out.print("1.Add Expense\n2.View Expense\n3.Delete Expense\n4.Update Expense\n5.View Summary\n6.Search by category\n7.Search by date\n8.Sort By amount\n9.Sort by amount in descending order\n10.Filter By amount\n11.Exit\n");
            System.out.print("Enter choice : ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {

                case 1:
                    System.out.print("Enter Category : ");
                    String category = sc.nextLine();
                    System.out.print("Enter amount : ");
                    double amount = sc.nextDouble();
                    sc.nextLine();
                    System.out.print("Enter date : ");
                    String date = sc.nextLine();
                    tracker.addExpense(category, amount, date);
                    break;

                case 2:
                    tracker.viewExpenses();
                    break;

                case 3:
                    System.out.print("Enter the ID of Expense to delete : ");
                    int id = sc.nextInt();
                    tracker.deleteExpense(id);
                    break;

                case 4:
                    System.out.println("Enter the ID of expense to update : ");
                    id = sc.nextInt();
                    sc.nextLine();
                    if (!tracker.isPresent(id)) {
                        System.out.println("Expense not found.");
                    } else {
                        System.out.print("Enter new Expense Category : ");
                        String newCat = sc.nextLine();

                        System.out.print("Enter new amount : ");
                        double newAmount = sc.nextDouble();
                        sc.nextLine();

                        System.out.print("Enter new date : ");
                        String newDate = sc.nextLine();

                        tracker.updateExpense(id, newCat, newAmount, newDate);
                    }
                    break;

                case 5:
                    tracker.summary();
                    break;

                case 6:
                    System.out.println("Enter the category to search : ");
                    String searchCategory = sc.nextLine();
                    tracker.searchByCategory(searchCategory);
                    break;

                case 7:
                    System.out.println("Enter the Date to search : ");
                    String searchDate = sc.nextLine();
                    tracker.searchByDate(searchDate);
                    break;

                case 8:
                    tracker.sortByAmount();
                    break;

                case 9:
                    tracker.sortByAmountDescending();
                    break;

                case 10:
                    System.out.println("Enter Start amount : ");
                    double min = sc.nextDouble();
                    System.out.println("Enter End Amount : ");
                    double max = sc.nextDouble();
                    tracker.filterByAmount(min, max);
                    break;

                case 11:
                    flag = false;
                    break;

                default:
                    System.out.println("Invalid Choice , Enter Correct Option.");
            }
        }
    }
}

