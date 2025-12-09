package com.example.finance;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public abstract class TransactionReportGenerator {

    private TransactionReportGenerator() {}

    public static void printBalanceReport(double totalBalance) {
        System.out.println("--- Report: Total Balance ---");
        System.out.printf("Total balance: %.2f UAH%n%n", totalBalance);
    }

    public static void printTransactionsCountByMonth(String monthYear, int count) {
        System.out.println("--- Report: Number of Transactions per Month ---");
        System.out.printf("Number of transactions for %s: %d%n%n", monthYear, count);
    }

    public static void printTopExpensesReport(List<Transaction> topExpenses) {
        System.out.println("--- Report: Top 10 Expenses ---");
        for (Transaction expense : topExpenses) {
            System.out.printf("%s: %.2f UAH%n", expense.getDescription(), expense.getAmount());
        }
        System.out.println();
    }

    public static void printMinMaxExpensesReport(Map<String, Double> minMaxStats, LocalDate start, LocalDate end) {
        System.out.printf("--- Report: Min/Max Expenses for Period (%s - %s) ---%n", start, end);
        System.out.printf("Largest single expense: %.2f UAH%n", minMaxStats.getOrDefault("max", 0.0));
        System.out.printf("Smallest single expense: %.2f UAH%n%n", minMaxStats.getOrDefault("min", 0.0));
    }

    public static void printExpenseReportWithVisualization(String title, Map<String, Double> expenseMap) {
        final int SYMBOL_VALUE = 1000;

        System.out.printf("--- %s ---%n", title);
        System.out.printf("(Each '*' symbol represents ~%d UAH in expenses)%n%n", SYMBOL_VALUE);

        for (Map.Entry<String, Double> entry : expenseMap.entrySet()) {
            double amount = entry.getValue();
            long starsCount = Math.round(Math.abs(amount) / SYMBOL_VALUE);
            String stars = "*".repeat((int) starsCount);

            System.out.printf("%-20s | %s (%.2f UAH)%n", entry.getKey(), stars, amount);
        }
        System.out.println();
    }
}