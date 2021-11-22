package com.excalibur.product;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;
import java.util.UUID;

public class Products {

    private static final Map<UUID, Product> products = new LinkedHashMap<>();
    private static Products instance = new Products();

    private static void addProduct(Product product) {
        products.put(product.id(), product);
    }

    private static void addSampleProducts() {
        addProduct(new Product(UUID.randomUUID(), "Product 1", new ProductVariant(UUID.randomUUID(), "Blac")));
        addProduct(new Product(UUID.randomUUID(), "Product 2", new ProductVariant(UUID.randomUUID(), "Blue")));
        addProduct(new Product(UUID.randomUUID(), "Product 3", new ProductVariant(UUID.randomUUID(), "Green")));
        addProduct(new Product(UUID.randomUUID(), "Product 4", new ProductVariant(UUID.randomUUID(), "Red")));
        addProduct(new Product(UUID.randomUUID(), "Product 5", new ProductVariant(UUID.randomUUID(), "Yellow")));
        addProduct(new Product(UUID.randomUUID(), "Product 6", new ProductVariant(UUID.randomUUID(), "Orange")));
    }

    static {
        addSampleProducts();
    }

    public static Products getInstance() {
        return instance;
    }

    public Product get(UUID id) {
        return products.get(id);
    }

    public List<Product> getAll() {
        return products.values().stream().toList();
    }

    public void add(Product product) {
        addProduct(product);
    }

    public void reset() {
        products.clear();
        addSampleProducts();
    }
}
