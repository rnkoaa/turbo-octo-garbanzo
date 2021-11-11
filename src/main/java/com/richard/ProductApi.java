package com.richard;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import java.util.List;
import java.util.UUID;
import reactor.core.publisher.Mono;

public interface ProductApi {

    @Get
    Mono<List<Product>> getProducts() ;

    @Post
    Mono<HttpResponse<Product>> create(@Body Product product) ;

	@Put("/{id}")
    Mono<Product> updateProduct(UUID id, @Body Product product);

    @Delete
    Mono<HttpResponse<?>> resetInventory() ;

    @Get("/{id}")
    Mono<Product> getProduct(UUID id) ;

}
