import org.example.BudgetNotebook;
import org.example.Category;
import org.example.Transaction;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;
public class BudgetNotebookTest {

    private BudgetNotebook budgetNotebook = new BudgetNotebook(new ArrayList<>());
    @Test
    public void addCategory() {
        Category category = new Category("Еда");
        budgetNotebook.addCategory(category.getName());
        assertNotNull(budgetNotebook.getCategoryByName("Еда"));
    }

    @Test
    public void addDuplicateCategory() {
        Category category1 = new Category("Развлечения");
        Category category2 = new Category("Развлечения");
        budgetNotebook.addCategory(category1.getName());
        budgetNotebook.addCategory(category2.getName());
        assertEquals(1, budgetNotebook.getCategories().size());
    }

    @Test
    public void addMccToCategory() {
        Category category1 = new Category("Еда");
        budgetNotebook.addCategory(category1.getName());
        budgetNotebook.addMccToCategory("1234", category1.getName());
        assertTrue(budgetNotebook.getCategoryByName(category1.getName()).getMcc().contains("1234"));

        budgetNotebook.addMccToCategory("1234", category1.getName());
        assertEquals(1, budgetNotebook.getCategoryByName(category1.getName()).getMcc().size());

        Category category2 = new Category("Развлечения");
        budgetNotebook.getCategories().add(category2);
        budgetNotebook.addMccToCategory("1234", category2.getName());
        assertEquals(0, budgetNotebook.getCategoryByName(category2.getName()).getMcc().size());
    }

    @Test
    public void addGroupToCategory() {
        Category category = new Category("Еда");
        budgetNotebook.addCategory(category.getName());

        Category subcategory1 = new Category("Рестораны");
        Category subcategory2 = new Category("Фастфуд");
        budgetNotebook.addGroupToCategory(category.getName(), new Category[]{subcategory1, subcategory2});

        assertTrue(budgetNotebook.getCategoryByName(category.getName()).getSubcategories().contains(subcategory1));
        assertTrue(budgetNotebook.getCategoryByName(category.getName()).getSubcategories().contains(subcategory2));
    }

    @Test
    public void deleteCategory() {
        Category category = new Category("Супермаркеты");
        budgetNotebook.addCategory(category.getName());
        budgetNotebook.deleteCategory(category.getName());
        assertNull(budgetNotebook.getCategoryByName(category.getName()));
    }


    @Test
    public void deleteNonExistingCategory() {
        Category category1 = new Category("Развлечения");
        Category category2 = new Category("Рестораны");
        budgetNotebook.addCategory(category1.getName());
        budgetNotebook.deleteCategory(category2.getName());
        assertNotNull(budgetNotebook.getCategoryByName(category1.getName()));
    }


    @Test
    public void addTransaction() {
        Category category = new Category("Еда");
        Transaction transaction = new Transaction(50.0, "январь");
        budgetNotebook.addCategory(category.getName());
        budgetNotebook.addTransaction(category.getName(), transaction.getValue(), transaction.getMonth());
        assertEquals(1, budgetNotebook.getCategoryByName(category.getName()).getTransactions().size());
    }

    @Test
    public void deleteTransaction() {
        Category category = new Category("Еда");
        Transaction transaction1 = new Transaction(50.0, "январь");
        budgetNotebook.addCategory(category.getName());
        budgetNotebook.addTransaction(category.getName(), transaction1.getValue(), transaction1.getMonth());
        budgetNotebook.deleteTransaction(category.getName(), transaction1.getValue(), transaction1.getMonth());
        assertEquals(0, budgetNotebook.getCategoryByName(category.getName()).getTransactions().size());
    }

    @Test
    public void showCategories() {
        budgetNotebook.addCategory("Еда");
        budgetNotebook.addCategory("Развлечения");
        budgetNotebook.addCategory("Транспорт");
        budgetNotebook.showCategories();
    }


    @Test
    public void showTransactionsInSelectedMonth() {
        Category category1 = new Category("Развлечения");
        Category category2 = new Category("Рестораны");
        budgetNotebook.addCategory(category1.getName());
        budgetNotebook.addCategory(category2.getName());

        Transaction transaction1 = new Transaction(1000, "март");
        Transaction transaction2 = new Transaction(1100, "март");
        budgetNotebook.addTransaction(category1.getName(), transaction1.getValue(), transaction1.getMonth());
        budgetNotebook.addTransaction(category2.getName(), transaction2.getValue(), transaction2.getMonth());

        budgetNotebook.showTransactionsInSelectedMonth("март");
    }

    @Test
    public void showTransactionsInSelectedMonthIfCategoriesEmpty() {
        Category category1 = new Category("Развлечения");
        Category category2 = new Category("Рестораны");

        Transaction transaction1 = new Transaction(1000, "март");
        Transaction transaction2 = new Transaction(1100, "март");
        budgetNotebook.addTransaction(category1.getName(), transaction1.getValue(), transaction1.getMonth());
        budgetNotebook.addTransaction(category2.getName(), transaction2.getValue(), transaction2.getMonth());

        budgetNotebook.showTransactionsInSelectedMonth("март");
    }

    @Test
    public void showTransactionsInSelectedCategory() {
        Category category1 = new Category("Развлечения");
        budgetNotebook.addCategory(category1.getName());

        Transaction transaction1 = new Transaction(1000, "март");
        Transaction transaction2 = new Transaction(1100, "апрель");
        budgetNotebook.addTransaction(category1.getName(), transaction1.getValue(), transaction1.getMonth());
        budgetNotebook.addTransaction(category1.getName(), transaction2.getValue(), transaction2.getMonth());

        budgetNotebook.showTransactionsInSelectedCategory(category1.getName());
    }

}
