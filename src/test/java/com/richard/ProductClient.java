package com.richard;

import io.micronaut.core.async.annotation.SingleResult;
import java.util.List;
import java.util.UUID;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.client.annotation.Client;
import reactor.core.publisher.Mono;

@Client("/product")
public interface ProductClient extends ProductApi{
//
//    @Get
//    @SingleResult
//    Mono<List<Product>> getAll();
//
//    @Post
//    @SingleResult
//    Mono<HttpResponse<Product>> create(@Body Product product);
//
//    @Put("/{id}")
//    @SingleResult
//    Mono<Product> updateProduct(UUID id, @Body Product product);
//
//    @Get("/{id}")
//    @SingleResult
//    Mono<Product> getProduct(UUID id);
//
//    @Delete
//    @SingleResult
//    Mono<?> resetInventory();
}
