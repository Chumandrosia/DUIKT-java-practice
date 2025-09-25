import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private Cart cart;
    private List<Order> orderHistory;

    public User(String name) {
        this.name = name;
        this.cart = new Cart();
        this.orderHistory = new ArrayList<>();
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
    }
}
