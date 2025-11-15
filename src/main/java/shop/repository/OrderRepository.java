package shop.repository;

import shop.model.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
// [SOLID: SRP] — только управление заказами как данными
public class OrderRepository {
    private final Map<String, Order> orders = new HashMap<>();

    public void save(Order order) {
        orders.put(order.getOrderId(), order);
    }

    public Order findById(String id) {
        return orders.get(id);
    }

    public List<Order> findAll() {
        return new ArrayList<>(orders.values());
    }
}