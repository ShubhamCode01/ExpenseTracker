package dev.lpa;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.InputMismatchException;
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
                    tracker.addExpense(category, amount, date);
                    break;

                case 2:
                    tracker.viewExpenses();
                    break;

                case 3:
                    int id = readID(sc);
                    tracker.deleteExpense(id);
                    break;

                case 4:
                    id = readID(sc);
                    sc.nextLine();
                    if (!tracker.isPresent(id)) {
                        System.out.println("Expense not found.");
                    } else {
                        String newCat = readCategory(sc);

                        double newAmount = readAmount(sc);
                        sc.nextLine();

                        LocalDate newDate = readDate(sc);

                        tracker.updateExpense(id, newCat, newAmount, newDate);
                    }
                    break;

                case 5:
                    tracker.summary();
                    break;

                case 6:
                    String searchCategory = readCategory(sc);
                    tracker.searchByCategory(searchCategory);
                    break;

                case 7:
                    LocalDate searchDate = readDate(sc);
                    tracker.searchByDate(searchDate);
                    break;

                case 8:
                    tracker.sortByAmount();
                    break;

                case 9:
                    tracker.sortByAmountDescending();
                    break;

                case 10:
                    while(true){
                        double min = readAmount(sc);
                        double max = readAmount(sc);
                        if(max >= min){
                            tracker.filterByAmount(min, max);
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

