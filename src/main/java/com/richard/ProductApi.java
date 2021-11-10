package com.richard;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Controller("/product")
public class ProductApi {
    private Products products = Products.getInstance();

    public ProductApi() {
    }

    @Get("/")
    public Mono<List<Product>> getProducts() {
        return Mono.just(products.getAll());
    }

    @Post
    public Mono<HttpResponse<Product>> create(@Body Product product) {
        var existing = products.get(product.id());
        if (existing != null) {
            return Mono.error(new IllegalArgumentException("Product already exists"));
        }
        products.add(product);
        return Mono.just(HttpResponse.created(product, location(product.id())));
    }


	@Put("/{id}")
	public Mono<Product> updateProduct(UUID id, @Body Product product) {
        var existing = products.get(product.id());
        if (existing == null) {
            return Mono.error(new IllegalArgumentException("Product does not exists"));
        }
        products.add(product);
        return Mono.just(product); 
    }


    @Delete("/")
    public Mono<?> resetInventory() {
        products.reset();
        return Mono.empty();
    }
    
    @Get("/{id}")
    public Mono<Product> getProduct(UUID id) {
        return Mono.just(products.get(id));
    }

    protected URI location(UUID id) {
        return URI.create("/product/" + id.toString());
    }
}
