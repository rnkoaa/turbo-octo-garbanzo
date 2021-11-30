package com.excalibur.functest;

import com.excalibur.product.Product;
import com.excalibur.product.ProductApi;
import io.micronaut.http.HttpResponse;
import java.util.List;
import java.util.UUID;
import reactor.core.publisher.Mono;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProductClient extends ProductApi {

    @Override
    @GET("/")
    Mono<List<Product>> getProducts();

    @Override
    @POST("/")
    Mono<HttpResponse<Product>> create(@Body Product product);

    @Override
    @PUT("/{id}")
    Mono<Product> updateProduct(@Path("id") UUID id, @Body Product product);

    @Override
    @DELETE("/")
    Mono<HttpResponse<?>> resetInventory();

    @Override
    @GET("/{id}")
    Mono<Product> getProduct(@Path("id") UUID id);
}
