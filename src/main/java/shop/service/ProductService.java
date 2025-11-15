package shop.service;

import shop.model.Product;
import shop.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

// [SOLID: SRP — Single Responsibility Principle]
// Этот класс отвечает ТОЛЬКО за бизнес-логику, связанную с товарами:
// поиск, фильтрация, рейтинг — всё, что требует "правил", а не просто данных.
public class ProductService {
    // [SOLID: DIP — Dependency Inversion Principle]
    // Зависимость от абстракции (ProductRepository — интерфейс по сути),
    // а не от конкретной реализации. Это позволяет подменить репозиторий в тестах.
    private final ProductRepository productRepository;
    // [SOLID: DIP] — внедрение зависимости через конструктор
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    // [DRY] — логика поиска по ключевому слову вынесена в один метод,
    // а не дублируется в Main или других сервисах.
    public List<Product> searchProducts(String keyword) {
        return productRepository.findAll().stream()
                .filter(p -> p.getName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }
    // [DRY] — повторное использование productRepository.findAll()
    // вместо дублирования цикла или SQL-запроса
    public List<Product> filterByPrice(double min, double max) {
        return productRepository.findAll().stream()
                .filter(p -> p.getPrice() >= min && p.getPrice() <= max)
                .collect(Collectors.toList());
    }

    public void rateProduct(long productId, int rating) {
        Product product = productRepository.findById(productId);
        if (product != null) {
            product.addRating(rating);
        }
    }
}