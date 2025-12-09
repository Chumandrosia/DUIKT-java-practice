import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class User {
    private String name;
    private Cart cart;
    private List<Order> orderHistory;
    private static final String ORDER_HISTORY_FILE = "order_history.txt";

    public User(String name) {
        this.name = name;
        this.cart = new Cart();
        this.orderHistory = new ArrayList<>();
        loadOrderHistory();
    }

    public String getName() {
        return name;
    }

    public Cart getCart() {
        return cart;
    }

    public List<Order> getOrderHistory() {
        return new ArrayList<>(orderHistory);
    }

    public void placeOrder() {
        if (cart.getProducts().isEmpty()) {
            System.out.println("Кошик порожній. Додайте товари перед оформленням замовлення.");
            return;
        }
        Order order = new Order(cart);
        orderHistory.add(order);
        System.out.println("Замовлення оформлено:");
        System.out.println(order);
        cart.clear();
        saveOrderHistory();
    }

    private void saveOrderHistory() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ORDER_HISTORY_FILE, false))) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
            for (Order order : orderHistory) {
                writer.println("=== ЗАМОВЛЕННЯ ===");
                writer.println("Користувач: " + name);
                writer.println("Дата: " + LocalDateTime.now().format(formatter));
                writer.println("Статус: " + order.getStatus());
                writer.println("Товари:");
                for (Product product : order.getProducts()) {
                    writer.println("  - " + product.getName() + " (ID: " + product.getId() + ", Ціна: " + product.getPrice() + " грн, Категорія: " + product.getCategory().getName() + ")");
                }
                writer.println("Загальна вартість: " + order.getTotalPrice() + " грн");
                writer.println("==================");
                writer.println();
            }
            System.out.println("Історію замовлень збережено у файл: " + ORDER_HISTORY_FILE);
        } catch (IOException e) {
            System.err.println("Помилка при збереженні історії: " + e.getMessage());
        }
    }

    private void loadOrderHistory() {
        File file = new File(ORDER_HISTORY_FILE);
        if (!file.exists()) {
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            System.out.println("Історію замовлень завантажено з файлу.");
        } catch (IOException e) {
            System.err.println("Помилка при завантаженні історії: " + e.getMessage());
        }
    }

    public void exportOrderHistoryToCSV(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("ID_Товару,Назва,Ціна,Категорія,Загальна_Вартість,Статус");
            for (Order order : orderHistory) {
                for (Product product : order.getProducts()) {
                    writer.printf("%d,%s,%.2f,%s,%.2f,%s%n", product.getId(), product.getName(), product.getPrice(), product.getCategory().getName(), order.getTotalPrice(), order.getStatus());
                }
            }
            System.out.println("Експортовано в CSV: " + filename);
        } catch (IOException e) {
            System.err.println("Помилка експорту: " + e.getMessage());
        }
    }

    public void printOrderStatistics() {
        if (orderHistory.isEmpty()) {
            System.out.println("Історія замовлень порожня.");
            return;
        }
        double totalSpent = 0;
        int totalItems = 0;
        for (Order order : orderHistory) {
            totalSpent += order.getTotalPrice();
            totalItems += order.getProducts().size();
        }
        System.out.println("\nСТАТИСТИКА ЗАМОВЛЕНЬ");
        System.out.println("Кількість замовлень: " + orderHistory.size());
        System.out.println("Загальна сума: " + String.format("%.2f", totalSpent) + " грн");
        System.out.println("Кількість товарів: " + totalItems);
        System.out.println("Середня вартість: " + String.format("%.2f", totalSpent / orderHistory.size()) + " грн\n");
    }
}
