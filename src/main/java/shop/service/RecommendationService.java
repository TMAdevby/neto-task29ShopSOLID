package shop.service;

import shop.model.Product;
import shop.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

// [SOLID: SRP] — только рекомендации, ничего больше
// [DRY] — повторное использование findByCategory из репозитория
public class RecommendationService {
    private final ProductRepository productRepository;

    public RecommendationService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    // [SOLID: OCP] — если завтра понадобится фильтрация по рейтингу + цене,
    // можно расширить, не меняя существующий код
    public List<Product> recommendByCategory(String category) {
        return productRepository.findByCategory(category).stream()
                .sorted((p1, p2) -> Double.compare(p2.getAverageRating(), p1.getAverageRating()))
                .limit(3)
                .collect(Collectors.toList());
    }
}
