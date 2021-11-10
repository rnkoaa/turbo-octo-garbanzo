package com.richard;

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
public interface ProductClient {

	@Get("/")
	public Mono<List<Product>> getAll();

	@Post("/")
	public Mono<HttpResponse<Product>> create(@Body Product product);

	@Put("/{id}")
	public Mono<Product> updateProduct(UUID id, @Body Product product);

	@Get("/{id}")
	public Mono<Product> getProduct(UUID id);

	@Delete("/")
	public Mono<?> resetInventory();
}
