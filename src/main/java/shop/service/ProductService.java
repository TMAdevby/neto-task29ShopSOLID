package shop.service;

import shop.model.Product;
import shop.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

// ✅ SRP: только логика работы с товарами
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> searchProducts(String keyword) {
        return productRepository.findAll().stream()
                .filter(p -> p.getName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

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