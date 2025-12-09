import java.util.Scanner;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Category electronics = new Category(1, "Електроніка");
        Category smartphones = new Category(2, "Смартфони");
        Category accessories = new Category(3, "Аксесуари");
        Product product1 = new Product(1, "Ноутбук", 19999.99, "Високопродуктивний ноутбук для роботи та ігор", electronics);
        Product product2 = new Product(2, "Смартфон", 12999.50, "Смартфон з великим екраном", smartphones);
        Product product3 = new Product(3, "Навушники", 2499.00, "Бездротові навушники з шумозаглушенням", accessories);
        Product product4 = new Product(4, "Планшет", 8999.00, "Планшет для роботи", electronics);
        Product product5 = new Product(5, "Клавіатура", 1299.99, "Механічна клавіатура", accessories);
        Product product6 = new Product(6, "Миша", 799.00, "Бездротова миша", accessories);
        ProductSearch productSearch = new ProductSearch();
        productSearch.addProduct(product1);
        productSearch.addProduct(product2);
        productSearch.addProduct(product3);
        productSearch.addProduct(product4);
        productSearch.addProduct(product5);
        productSearch.addProduct(product6);
        User user = new User("Клієнт");
        System.out.println("Ласкаво просимо до Інтернет-магазину!");
        while (true) {
            System.out.println("\nМЕНЮ:");
            System.out.println("1 - Переглянути всі товари");
            System.out.println("2 - Додати товар до кошика");
            System.out.println("3 - Переглянути кошик");
            System.out.println("4 - Зробити замовлення");
            System.out.println("5 - Видалити товар з кошика");
            System.out.println("6 - Переглянути історію замовлень");
            System.out.println("7 - Статистика замовлень");
            System.out.println("8 - ПОШУК товарів");
            System.out.println("9 - Експорт історії в CSV");
            System.out.println("0 - Вийти");
            System.out.print("Ваш вибір: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("\nКАТАЛОГ ТОВАРІВ:");
                    for (Product p : productSearch.getAllProducts()) {
                        System.out.println(p);
                    }
                    break;
                case 2:
                    System.out.print("Введіть ID товару: ");
                    int addId = scanner.nextInt();
                    scanner.nextLine();
                    Product toAdd = null;
                    for (Product p : productSearch.getAllProducts()) {
                        if (p.getId() == addId) {
                            toAdd = p;
                            break;
                        }
                    }
                    if (toAdd != null) {
                        user.getCart().addProduct(toAdd);
                        System.out.println("Товар додано!");
                    } else {
                        System.out.println("Товар не знайдено.");
                    }
                    break;
                case 3:
                    System.out.println("\n" + user.getCart());
                    break;
                case 4:
                    user.placeOrder();
                    break;
                case 5:
                    if (user.getCart().getProducts().isEmpty()) {
                        System.out.println("Кошик порожній.");
                        break;
                    }
                    System.out.print("Введіть ID товару: ");
                    int removeId = scanner.nextInt();
                    scanner.nextLine();
                    Product toRemove = null;
                    for (Product p : user.getCart().getProducts()) {
                        if (p.getId() == removeId) {
                            toRemove = p;
                            break;
                        }
                    }
                    if (toRemove != null) {
                        user.getCart().removeProduct(toRemove);
                        System.out.println("Товар видалено.");
                    } else {
                        System.out.println("Товар не знайдено.");
                    }
                    break;
                case 6:
                    if (user.getOrderHistory().isEmpty()) {
                        System.out.println("Історія замовлень порожня.");
                    } else {
                        System.out.println("\nІСТОРІЯ ЗАМОВЛЕНЬ:");
                        int orderNum = 1;
                        for (Order o : user.getOrderHistory()) {
                            System.out.println("\nЗамовлення #" + orderNum++);
                            System.out.println(o);
                            System.out.println("---");
                        }
                    }
                    break;
                case 7:
                    user.printOrderStatistics();
                    break;
                case 8:
                    searchMenu(scanner, productSearch);
                    break;
                case 9:
                    System.out.print("Введіть ім'я файлу (наприклад, orders.csv): ");
                    String filename = scanner.nextLine();
                    if (!filename.endsWith(".csv")) {
                        filename += ".csv";
                    }
                    user.exportOrderHistoryToCSV(filename);
                    break;
                case 0:
                    System.out.println("Дякуємо за використання магазину!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Невідома опція.");
                    break;
            }
        }
    }
    private static void searchMenu(Scanner scanner, ProductSearch productSearch) {
        while (true) {
            System.out.println("\nМЕНЮ ПОШУКУ:");
            System.out.println("1 - Пошук за назвою");
            System.out.println("2 - Пошук за категорією");
            System.out.println("3 - Пошук за ціновим діапазоном");
            System.out.println("4 - Комбінований пошук");
            System.out.println("5 - Переглянути категорії");
            System.out.println("0 - Повернутися");
            System.out.print("Ваш вибір: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.print("Введіть назву товару: ");
                    String searchTerm = scanner.nextLine();
                    List<Product> results = productSearch.searchByName(searchTerm);
                    productSearch.displaySearchResults(results, "назва: " + searchTerm);
                    break;
                case 2:
                    System.out.print("Введіть назву категорії: ");
                    String category = scanner.nextLine();
                    List<Product> catResults = productSearch.searchByCategory(category);
                    productSearch.displaySearchResults(catResults, "категорія: " + category);
                    break;
                case 3:
                    System.out.print("Мінімальна ціна: ");
                    double minPrice = scanner.nextDouble();
                    System.out.print("Максимальна ціна: ");
                    double maxPrice = scanner.nextDouble();
                    scanner.nextLine();
                    List<Product> priceResults = productSearch.searchByPriceRange(minPrice, maxPrice);
                    productSearch.displaySearchResults(priceResults, "ціна: " + minPrice + "-" + maxPrice);
                    if (!priceResults.isEmpty()) {
                        System.out.println("Сортувати? (1-зростання, 2-спадання, 0-ні)");
                        int sortChoice = scanner.nextInt();
                        scanner.nextLine();
                        if (sortChoice == 1) {
                            priceResults = productSearch.sortByPriceAsc(priceResults);
                            System.out.println("\nВідсортовано за зростанням:");
                            productSearch.displaySearchResults(priceResults, "відсортовано");
                        } else if (sortChoice == 2) {
                            priceResults = productSearch.sortByPriceDesc(priceResults);
                            System.out.println("\nВідсортовано за спаданням:");
                            productSearch.displaySearchResults(priceResults, "відсортовано");
                        }
                    }
                    break;
                case 4:
                    System.out.print("Введіть запит: ");
                    String combSearch = scanner.nextLine();
                    List<Product> combResults = productSearch.searchByNameOrCategory(combSearch);
                    productSearch.displaySearchResults(combResults, combSearch);
                    break;
                case 5:
                    List<String> categories = productSearch.getAllCategories();
                    System.out.println("\nКАТЕГОРІЇ:");
                    for (String cat : categories) {
                        System.out.println("• " + cat);
                    }
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Невідома опція.");
                    break;
            }
        }
    }
}
