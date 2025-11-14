package shop.service;

import shop.model.Product;
import shop.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

// ✅ SRP: только управление корзиной
public class CartService {
    private final ProductRepository productRepository;
    private final List<Product> cart = new ArrayList<>();

    public CartService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void addToCart(long productId) {
        Product product = productRepository.findById(productId);
        if (product != null) {
            cart.add(product);
        }
    }

    public List<Product> getCart() {
        return new ArrayList<>(cart);
    }

    public void clear() {
        cart.clear();
    }
}