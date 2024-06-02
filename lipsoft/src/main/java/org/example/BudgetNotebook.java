package org.example;

import lombok.*;
import java.util.*;
import java.util.stream.*;

@Getter
@Setter
@AllArgsConstructor
public class BudgetNotebook {
List <Category> categories;

    public Category getCategoryByName(String name){
        return categories.stream()
                .filter(x -> x.getName().equals(name))
                .findFirst().orElse(null);
    }

    public void addCategory(String name){
        if(name == null){
            System.out.println("Name must be not null");
            return;
        }
        if (categories.stream().anyMatch(category -> category.getName().equals(name)))
        {
            System.out.println("Category with this name already exists");
            return;
        }
        categories.add(new Category(name));
        System.out.println("Created new category " + name);
    }

    public void addMccToCategory(String newMcc, String name) {
        Category category1 = getCategoryByName(name);
        if (category1 != null) {
            if (category1.containsMcc(newMcc)) {
                System.out.println("This mcc already exist in category " + name);
                return;
            }
            else if (categories.stream().anyMatch(category2 -> category2.containsMcc(newMcc) && !category2.equals(category1))) {
                    System.out.println("This mcc already exist for another category");
                    return;
            }
            category1.addMcc(newMcc);
            System.out.println("Added new " + newMcc + " to category " + name);
        }
    }

    public void addGroupToCategory(String name, Category[] subcategories) {
        Category category = getCategoryByName(name);
        if (category != null) {
            category.getSubcategories().addAll(Arrays.asList(subcategories));
            System.out.println("Add new group subcategories to category " + name);
        }
    }


    public void deleteCategory(String name) {
        Category category = getCategoryByName(name);
        if (category != null) {
            categories.remove(category);
            System.out.println("Category removed from category");
        } else {
            System.out.println("Category with this name doesn't exist");
        }
    }

    public void addTransaction(String name, double value, String month){
        Category category = getCategoryByName(name);
        if (category != null){
            category.getTransactions().add(new Transaction(value, month));
            System.out.println("Add transaction to category " + name);
        }
    }

    public  void deleteTransaction(String name, double value, String month){
        Category category = getCategoryByName(name);
        if (category != null){
            List<Transaction> transactions = category.getTransactions();
            transactions.removeIf(transaction -> transaction.getMonth().equals(month) && transaction.getValue() == value);
            System.out.println("Transaction deleted");
        }
    }

    public void showCategories(){
        categories.stream()
                .map(Category::getName)
                .forEach(System.out::println);
    }

    public void showTransactionsInSelectedMonth(String month){
        if (categories.isEmpty()) {
            System.out.println("Без категорий + 0р + 0%");
            return;
        }
        double total = categories.stream()
                .flatMap(category -> category.getTransactions().stream())
                .filter(transaction -> transaction.getMonth().equals(month))
                .mapToDouble(Transaction::getValue)
                .sum();

        categories.stream()
                .forEach(category -> {
                    double totalInCategory = category.getTransactions().stream()
                            .filter(transaction -> transaction.getMonth().equals(month))
                            .mapToDouble(Transaction::getValue)
                            .sum();
                    double percent = Math.round((totalInCategory / total) * 100);
                    System.out.printf(category.getName() + " " + totalInCategory + "p" + " " + percent + "%%" + "\n");
                });
    }

    public void showTransactionsInSelectedCategory(String name) {
        Category category = getCategoryByName(name);
        category.getTransactions().stream()
                .collect(Collectors.groupingBy(Transaction::getMonth, Collectors.summingDouble(Transaction::getValue)))
                .forEach((month, value) -> System.out.println(month + " " + value + "р"));
    }

}
