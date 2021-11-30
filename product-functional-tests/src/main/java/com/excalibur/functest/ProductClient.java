package com.excalibur.functest;

import com.excalibur.product.Product;
import com.excalibur.product.ProductApi;
import io.micronaut.http.HttpResponse;
import java.util.List;
import java.util.UUID;
import okhttp3.Response;
import okhttp3.ResponseBody;
import reactor.core.publisher.Mono;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProductClient {

    @GET("/product")
    Mono<List<Product>> getProducts();

    @POST("/product")
    Mono<Product> create(@Body Product product);

    @PUT("/product/{id}")
    Mono<Product> updateProduct(@Path("id") UUID id, @Body Product product);

    @DELETE("/product")
    Mono<Void> resetProducts();

    @GET("/product/{id}")
    Mono<Product> getProduct(@Path("id") UUID id);
}
