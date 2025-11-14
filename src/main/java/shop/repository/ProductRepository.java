package shop.repository;

import shop.model.Product;

import java.util.*;
import java.util.stream.Collectors;

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

    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    public List<Product> findByCategory(String category) {
        return products.values().stream()
                .filter(p -> p.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    public Product findById(long id) {
        return products.get(id);
    }
}
