package shop.repository;

import shop.model.Product;

import java.util.*;
import java.util.stream.Collectors;
// [SOLID: SRP — Single Responsibility Principle]
// Этот класс отвечает только за хранение и извлечение данных.
// Никакой бизнес-логики: фильтрации, поиска по смыслу.
public class ProductRepository {
    private final Map<Long, Product> products = new HashMap<>();

    public ProductRepository() {
        // Инициализация тестовых данных
        addProduct(new Product(1L, "Молоко", "Молочка", 2.5));
        addProduct(new Product(2L, "Хлеб", "Хлебобулочные", 1.2));
        addProduct(new Product(3L, "Сыр", "Молочка", 8.0));
        addProduct(new Product(4L, "Кефир", "Молочка", 2.0));
    }

    public void addProduct(Product product) {
        products.put(product.getId(), product);
    }
    // [SOLID: SRP] — только "дай все товары", без обработки
    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }
    // [SOLID: SRP] — только "дай по категории", без логики сортировки или фильтрации
    public List<Product> findByCategory(String category) {
        return products.values().stream()
                .filter(p -> p.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    public Product findById(long id) {
        return products.get(id);
    }
}
