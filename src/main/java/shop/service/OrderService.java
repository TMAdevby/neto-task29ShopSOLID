package shop.service;

import shop.model.Order;
import shop.model.OrderStatus;
import shop.repository.OrderRepository;

import java.util.List;

// ✅ SRP: только управление заказами
// ✅ DIP: зависит от репозитория (абстракции — через конструктор)
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartService cartService;

    public OrderService(OrderRepository orderRepository, CartService cartService) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
    }

    public Order createOrder() {
        Order order = new Order(cartService.getCart());
        orderRepository.save(order);
        cartService.clear();
        return order;
    }

    public Order getOrder(String orderId) {
        return orderRepository.findById(orderId);
    }

    public void updateOrderStatus(String orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId);
        if (order != null) {
            order.setStatus(status);
        }
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Повтор заказа
    public Order repeatOrder(String orderId) {
        Order old = orderRepository.findById(orderId);
        if (old != null) {
            Order newOrder = new Order(old.getProducts());
            orderRepository.save(newOrder);
            return newOrder;
        }
        return null;
    }
}