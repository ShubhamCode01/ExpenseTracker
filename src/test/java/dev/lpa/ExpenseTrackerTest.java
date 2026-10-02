package dev.lpa;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpenseTrackerTest {

    @Test
    void addExpense_shouldAddExpense() {
        InMemoryExpenseRepository repository = new InMemoryExpenseRepository();

        ExpenseTracker tracker = new ExpenseTracker(repository);

        LocalDate date = LocalDate.of(2026, 9, 30);

        tracker.addExpense("Food", 500, date);

        assertEquals(1, repository.getExpenses().size());

        Expense savedExpense = repository.getExpenses().get(0);

        assertEquals("Food", savedExpense.getCategory());
        assertEquals(500, savedExpense.getAmount());
        assertEquals(date, savedExpense.getDate());
    }

    @Test
    void deleteExpense_shouldRemoveExpense() {
        InMemoryExpenseRepository repository = new InMemoryExpenseRepository();

        Expense expense = new Expense(
                "Food",
                500,
                LocalDate.of(2026, 10, 2)
        );

        repository.getExpenses().add(expense);

        ExpenseTracker tracker = new ExpenseTracker(repository);

        boolean deleted = tracker.deleteExpense(expense.getID());

        assertEquals(true, deleted);
        assertEquals(0, tracker.searchByCategory("Food").size());
    }

    @Test
    void updateExpense_shouldUpdateExpense() {
        InMemoryExpenseRepository repository = new InMemoryExpenseRepository();

        Expense expense = new Expense("Food", 500, LocalDate.of(2026, 9, 30));
        repository.getExpenses().add(expense);

        ExpenseTracker tracker = new ExpenseTracker(repository);

        int id = expense.getID();

        tracker.updateExpense(id,"Travel",200, LocalDate.of(2026, 9, 30));

        Expense updatedExpense = repository.getExpenses().get(0);

        assertEquals("Travel",updatedExpense.getCategory());
        assertEquals(200,updatedExpense.getAmount());
        assertEquals(LocalDate.of(2026, 9, 30),updatedExpense.getDate());
    }

    @Test
    void sortByAmount_shouldSortAscending() {

        InMemoryExpenseRepository repository = new InMemoryExpenseRepository();

        Expense expense1 = new Expense("Food", 500, LocalDate.of(2026, 9, 30));
        Expense expense2 = new Expense("Travel", 200, LocalDate.of(2026, 9, 30));
        Expense expense3 = new Expense("Shopping", 800, LocalDate.of(2026, 9, 30));

        repository.getExpenses().add(expense1);
        repository.getExpenses().add(expense2);
        repository.getExpenses().add(expense3);

        ExpenseTracker tracker = new ExpenseTracker(repository);

        List<Expense> sortedExpenses = tracker.sortByAmount();

        assertEquals(200, sortedExpenses.get(0).getAmount());
        assertEquals(500, sortedExpenses.get(1).getAmount());
        assertEquals(800, sortedExpenses.get(2).getAmount());
    }

    @Test
    void sortByAmountDescending_shouldSortDescending() {

        InMemoryExpenseRepository repository = new InMemoryExpenseRepository();

        Expense expense1 = new Expense("Food", 500, LocalDate.of(2026, 9, 30));
        Expense expense2 = new Expense("Travel", 200, LocalDate.of(2026, 9, 30));
        Expense expense3 = new Expense("Shopping", 800, LocalDate.of(2026, 9, 30));

        repository.getExpenses().add(expense1);
        repository.getExpenses().add(expense2);
        repository.getExpenses().add(expense3);

        ExpenseTracker tracker = new ExpenseTracker(repository);

        List<Expense> sortedExpenses = tracker.sortByAmountDescending();

        assertEquals(800, sortedExpenses.get(0).getAmount());
        assertEquals(500, sortedExpenses.get(1).getAmount());
        assertEquals(200, sortedExpenses.get(2).getAmount());
    }

    @Test
    void filterByAmount_shouldReturnExpensesWithinRange() {

        InMemoryExpenseRepository repository = new InMemoryExpenseRepository();

        Expense expense1 = new Expense("Food", 200, LocalDate.of(2026, 9, 30));
        Expense expense2 = new Expense("Travel", 500, LocalDate.of(2026, 9, 30));
        Expense expense3 = new Expense("Shopping", 1000, LocalDate.of(2026, 9, 30));

        repository.getExpenses().add(expense1);
        repository.getExpenses().add(expense2);
        repository.getExpenses().add(expense3);

        ExpenseTracker tracker = new ExpenseTracker(repository);

        List<Expense> filteredExpenses = tracker.filterByAmount(300, 700);

        assertEquals(1, filteredExpenses.size());
        assertEquals(500, filteredExpenses.get(0).getAmount());
    }

    @Test
    void searchByCategory_shouldReturnMatchingExpenses() {

        InMemoryExpenseRepository repository = new InMemoryExpenseRepository();

        Expense expense1 = new Expense("Food", 500, LocalDate.of(2026, 9, 30));
        Expense expense2 = new Expense("Travel", 200, LocalDate.of(2026, 9, 30));
        Expense expense3 = new Expense("Food", 800, LocalDate.of(2026, 9, 30));

        repository.getExpenses().add(expense1);
        repository.getExpenses().add(expense2);
        repository.getExpenses().add(expense3);

        ExpenseTracker tracker = new ExpenseTracker(repository);

        List<Expense> result = tracker.searchByCategory("Food");

        assertEquals(2, result.size());
        assertEquals(500, result.get(0).getAmount());
        assertEquals(800, result.get(1).getAmount());
    }

    @Test
    void searchByDate_shouldReturnMatchingExpenses() {

        InMemoryExpenseRepository repository = new InMemoryExpenseRepository();

        LocalDate date1 = LocalDate.of(2026, 9, 30);
        LocalDate date2 = LocalDate.of(2026, 9, 29);

        Expense expense1 = new Expense("Food", 500, date1);
        Expense expense2 = new Expense("Travel", 200, date2);
        Expense expense3 = new Expense("Shopping", 800, date1);

        repository.getExpenses().add(expense1);
        repository.getExpenses().add(expense2);
        repository.getExpenses().add(expense3);

        ExpenseTracker tracker = new ExpenseTracker(repository);

        List<Expense> result = tracker.searchByDate(date1);

        assertEquals(2, result.size());
        assertEquals(500, result.get(0).getAmount());
        assertEquals(800, result.get(1).getAmount());
    }

    @Test
    void summary_shouldCalculateCorrectValues() {

        InMemoryExpenseRepository repository = new InMemoryExpenseRepository();

        repository.getExpenses().add(
                new Expense("Food", 500, LocalDate.of(2026, 9, 30))
        );

        repository.getExpenses().add(
                new Expense("Travel", 200, LocalDate.of(2026, 9, 30))
        );

        repository.getExpenses().add(
                new Expense("Food", 300, LocalDate.of(2026, 9, 30))
        );

        ExpenseTracker tracker = new ExpenseTracker(repository);

        ExpenseSummary summary = tracker.summary();

        assertEquals(1000, summary.getTotalExpense());
        assertEquals(3, summary.getNumberOfExpenses());
        assertEquals(1000.0 / 3, summary.getAverageExpense());
        assertEquals(500, summary.getHighestExpense());
        assertEquals("Food", summary.getHighestExpenseCategory());

        assertEquals(800, summary.getCategoryBreakdown().get("Food"));
        assertEquals(200, summary.getCategoryBreakdown().get("Travel"));
    }

    @Test
    void loadingExpenses_shouldContinueFromHighestId() {
        Expense expense1 = new Expense(10, "Food", 500,
                LocalDate.of(2026, 10, 1));

        Expense expense2 = new Expense(5, "Travel", 300,
                LocalDate.of(2026, 10, 2));

        Expense newExpense = new Expense("Shopping", 700,
                LocalDate.of(2026, 10, 3));

        assertEquals(11, newExpense.getID());
    }
}


class InMemoryExpenseRepository implements ExpenseRepository {
    private List<Expense> expenses = new ArrayList<>();

    @Override
    public void saveExpenses(List<Expense> expenses) {
        this.expenses = new ArrayList<>(expenses);
    }

    @Override
    public List<Expense> loadExpenses() {
        return new ArrayList<>(expenses);
    }

    public List<Expense> getExpenses() {
        return expenses;
    }
}