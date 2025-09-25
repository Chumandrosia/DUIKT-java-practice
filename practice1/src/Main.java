import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Створення категорій
        Category electronics = new Category(1, "Електроніка");
        Category smartphones = new Category(2, "Смартфони");
        Category accessories = new Category(3, "Аксесуари");
        // Створення об'єктів класу Product з вказівкою категорії
        Product product1 = new Product(1, "Ноутбук", 19999.99, "Високопродуктивний ноутбук для роботи та ігор", electronics);
        Product product2 = new Product(2, "Смартфон", 12999.50, "Смартфон з великим екраном…", smartphones);
        Product product3 = new Product(3, "Навушники", 2499.00, "Бездротові навушники з шумозаглушенням", accessories);

        User user = new User("Клієнт");

        while (true) {
            System.out.println("\nВиберіть опцію:");
            System.out.println("1 - Переглянути список товарів");
            System.out.println("2 - Додати товар до кошика");
            System.out.println("3 - Переглянути кошик");
            System.out.println("4 - Зробити замовлення");
            System.out.println("5 - Видалити товар з кошика");
            System.out.println("6 - Переглянути історію замовлень");
            System.out.println("0 - Вийти");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println(product1);
                    System.out.println(product2);
                    System.out.println(product3);
                    break;
                case 2:
                    System.out.println("Введіть ID товару для додавання до кошика:");
                    int addId = scanner.nextInt();
                    if (addId == 1) user.getCart().addProduct(product1);
                    else if (addId == 2) user.getCart().addProduct(product2);
                    else if (addId == 3) user.getCart().addProduct(product3);
                    else System.out.println("Товар з таким ID не знайдено");
                    break;
                case 3:
                    System.out.println(user.getCart());
                    break;
                case 4:
                    user.placeOrder();
                    break;
                case 5:
                    System.out.println("Введіть ID товару для видалення з кошика:");
                    int removeId = scanner.nextInt();
                    Product toRemove = null;
                    for (Product p : user.getCart().getProducts()) {
                        if (p.getId() == removeId) {
                            toRemove = p;
                            break;
                        }
                    }
                    if (toRemove != null) {
                        user.getCart().removeProduct(toRemove);
                        System.out.println("Товар видалено з кошика.");
                    } else {
                        System.out.println("Товар з таким ID не знайдено у кошику.");
                    }
                    break;
                case 6:
                    if (user.getOrderHistory().isEmpty()) {
                        System.out.println("Історія замовлень порожня.");
                    } else {
                        System.out.println("Історія замовлень:");
                        for (Order o : user.getOrderHistory()) {
                            System.out.println(o);
                            System.out.println("----");
                        }
                    }
                    break;
                case 0:
                    System.out.println("Дякуємо, що використовували наш магазин!");
                    return;
                default:
                    System.out.println("Невідома опція. Спробуйте ще раз.");
                    break;
            }
        }
    }
}

