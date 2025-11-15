package shop.model;

// [SOLID: OCP — Open/Closed Principle]
// Использование enum вместо строк ("created", "shipped") позволяет легко расширять статусы
// без изменения логики заказа. Добавишь новый статус — ничего переписывать не нужно.
public enum OrderStatus {
    CREATED,
    PROCESSING,
    SHIPPED,
    DELIVERED,
    CANCELLED
}
