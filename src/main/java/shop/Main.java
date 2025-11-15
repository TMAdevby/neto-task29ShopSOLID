package shop;

import shop.model.Product;
import shop.model.Order;
import shop.model.OrderStatus;
import shop.repository.ProductRepository;
import shop.repository.OrderRepository;
import shop.service.*;

import java.util.Scanner;
// [DRY] — вся логика взаимодействия вынесена в сервисы,
// в main только оркестрация, без дублирования кода
public class Main {
    public static void main(String[] args) {

        ProductRepository productRepo = new ProductRepository();
        OrderRepository orderRepo = new OrderRepository();
// [SOLID: DIP] — сервисы получают зависимости
        ProductService productService = new ProductService(productRepo);
        CartService cartService = new CartService(productRepo);
        OrderService orderService = new OrderService(orderRepo, cartService);
        RecommendationService recService = new RecommendationService(productRepo);

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Интернет-магазин ---");
            System.out.println("1. Показать все товары");
            System.out.println("2. Добавить в корзину (по ID)");
            System.out.println("3. Просмотреть корзину и оформить заказ");
            System.out.println("4. Отследить заказ");
            System.out.println("5. Повторить заказ");
            System.out.println("6. Оценить товар");
            System.out.println("0. Выход");
            System.out.print("Выберите действие: ");

            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    // [DRY] — вызов одного метода вместо повторения цикла
                    productService.getAllProducts().forEach(p ->
                            System.out.println(p.getId() + ". " + p.getName() + " - " + p.getPrice() + " руб. (" + p.getCategory() + ")"));
                    break;

                case 2:
                    System.out.print("Введите ID товара: ");
                    long id = sc.nextLong();
                    cartService.addToCart(id);
                    System.out.println("Товар добавлен в корзину.");
                    break;

                case 3:
                    var cart = cartService.getCart();
                    if (cart.isEmpty()) {
                        System.out.println("Корзина пуста.");
                    } else {
                        System.out.println("Ваша корзина:");
                        cart.forEach(p -> System.out.println("- " + p.getName()));
                        System.out.println("Оформить заказ? (y/n)");
                        if ("y".equalsIgnoreCase(sc.next())) {
                            Order order = orderService.createOrder();
                            System.out.println("Заказ создан! Номер: " + order.getOrderId());
                        }
                    }
                    break;

                case 4:
                    System.out.print("Введите номер заказа: ");
                    String orderId = sc.next();
                    Order order = orderService.getOrder(orderId);
                    if (order != null) {
                        System.out.println("Статус заказа: " + order.getStatus());
                        // Пример рекомендаций
                        if (!order.getProducts().isEmpty()) {
                            String cat = order.getProducts().get(0).getCategory();
                            var recs = recService.recommendByCategory(cat);
                            if (!recs.isEmpty()) {
                                System.out.println("Рекомендуем:");
                                recs.forEach(p -> System.out.println("- " + p.getName() + " (рейтинг: " + p.getAverageRating() + ")"));
                            }
                        }
                    } else {
                        System.out.println("Заказ не найден.");
                    }
                    break;

                case 5:
                    System.out.print("Номер заказа для повтора: ");
                    orderId = sc.next();
                    Order newOrder = orderService.repeatOrder(orderId);
                    if (newOrder != null) {
                        System.out.println("Новый заказ создан: " + newOrder.getOrderId());
                    } else {
                        System.out.println("Не удалось повторить заказ.");
                    }
                    break;

                case 6:
                    System.out.print("ID товара: ");
                    long prodId = sc.nextLong();
                    System.out.print("Оценка (1-5): ");
                    int rating = sc.nextInt();
                    try {
                        productService.rateProduct(prodId, rating);
                        System.out.println("Спасибо за оценку!");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Ошибка: " + e.getMessage());
                    }
                    break;

                case 0:
                    System.out.println("До свидания!");
                    return;

                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }
}
