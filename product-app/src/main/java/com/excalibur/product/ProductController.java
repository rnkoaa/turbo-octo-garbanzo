package com.excalibur.product;

import com.excalibur.product.data.Products;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import reactor.core.publisher.Mono;

@Controller("/product")
public class ProductController implements ProductApi {

    private final Products products = Products.getInstance();

    @Override
    public Mono<List<Product>> getProducts() {
        return Mono.just(products.getAll());
    }

    @Override
    public Mono<HttpResponse<Product>> create(@Body Product product) {
        var existing = products.get(product.id());
        if (existing != null) {
            return Mono.error(new IllegalArgumentException("Product already exists"));
        }
        products.add(product);
        return Mono.just(HttpResponse.created(product, location(product.id())));
    }

    @Override
    public Mono<Product> updateProduct(UUID id, @Body Product product) {
        var existing = products.get(product.id());
        if (existing == null) {
            return Mono.error(new IllegalArgumentException("Product does not exists"));
        }
        products.add(product);
        return Mono.just(product);
    }

    @Override
    public Mono<HttpResponse<?>> resetInventory() {
        products.reset();
        return Mono.just(HttpResponse.noContent());
    }

    @Override
    public Mono<Product> getProduct(UUID id) {
        return Mono.just(products.get(id));
    }

    @Override
    public Mono<HttpResponse<?>> deleteProduct(UUID id) {
        products.delete(id);
        return Mono.just(HttpResponse.noContent());
    }

    protected URI location(UUID id) {
        return URI.create("/product/" + id.toString());
    }
}
