package shop.service;

import shop.model.Product;
import shop.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

// ✅ SRP: только рекомендации
public class RecommendationService {
    private final ProductRepository productRepository;

    public RecommendationService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> recommendByCategory(String category) {
        return productRepository.findByCategory(category).stream()
                .sorted((p1, p2) -> Double.compare(p2.getAverageRating(), p1.getAverageRating()))
                .limit(3)
                .collect(Collectors.toList());
    }
}
