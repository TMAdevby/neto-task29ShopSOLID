package shop.model;

import java.util.Objects;

public class Product {
    private final long id;
    private final String name;
    private final String category;
    private final double price;
    private double averageRating;
    private int ratingCount;

    public Product(long id, String name, String category, double price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.averageRating = 0.0;
        this.ratingCount = 0;
    }

    public long getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public double getAverageRating() { return averageRating; }

    public void addRating(int rating) {
        if (rating < MIN_RATING || rating > MAX_RATING) {
            throw new IllegalArgumentException("Rating must be between " + MIN_RATING + " and " + MAX_RATING);
        }
        this.averageRating = (this.averageRating * this.ratingCount + rating) / (this.ratingCount + 1);
        this.ratingCount++;
    }

    public static final int MIN_RATING = 1;
    public static final int MAX_RATING = 5;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
