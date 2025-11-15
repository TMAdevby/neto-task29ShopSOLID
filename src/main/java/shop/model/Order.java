package shop.model;

import java.util.List;
import java.util.UUID;

public class Order {
    private final String orderId;
    private final List<Product> products;
    private OrderStatus status;

    public Order(List<Product> products) {
        // [SOLID: SRP + DRY]
        // Генерация уникального ID вынесена в одну строчку через UUID.
        // Не дублируется логика генерации ID в разных местах.
        this.orderId = UUID.randomUUID().toString();
        this.products = products;
        this.status = OrderStatus.CREATED;
    }

    public String getOrderId() { return orderId; }
    public List<Product> getProducts() { return products; }
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
}