package shop.model;

import java.util.List;
import java.util.UUID;

public class Order {
    private final String orderId;
    private final List<Product> products;
    private OrderStatus status;

    public Order(List<Product> products) {
        this.orderId = UUID.randomUUID().toString();
        this.products = products;
        this.status = OrderStatus.CREATED;
    }

    // Геттеры и сеттеры
    public String getOrderId() { return orderId; }
    public List<Product> getProducts() { return products; }
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
}