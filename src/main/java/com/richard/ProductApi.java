package com.richard;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.annotation.Status;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import reactor.core.publisher.Mono;

public interface ProductApi {

    @Get
    @Operation(summary = "get all products",
        description = "get all products"
    )
    @ApiResponse(responseCode = "400", description = "invalid product request")
    @ApiResponse(responseCode = "200", description = "found products")
    @ApiResponse(responseCode = "404", description = "product not found")
    @Tag(name = "product")
    Mono<List<Product>> getProducts();

    @Post
    @Operation(summary = "creates a new product",
        description = "creates a new product."
    )
    @ApiResponse(responseCode = "400", description = "invalid product request")
    @ApiResponse(responseCode = "201", description = "successfully created product")
    @Tag(name = "product")
    @Status(HttpStatus.CREATED)
    Mono<HttpResponse<Product>> create(@Body Product product);

    @Put("/{id}")
    @Operation(summary = "update product",
        description = "update product"
    )
    @ApiResponse(responseCode = "400", description = "invalid product request")
    @ApiResponse(responseCode = "200", description = "found products")
    @ApiResponse(responseCode = "404", description = "product not found")
    @Tag(name = "product")
    Mono<Product> updateProduct(UUID id, @Body Product product);

    @Delete
    @Operation(summary = "get single productx",
        description = "get single product"
    )
    @ApiResponse(responseCode = "400", description = "invalid product request")
    @ApiResponse(responseCode = "204", description = "product successfully deleted")
    @ApiResponse(responseCode = "404", description = "product not found")
    @Tag(name = "product")
    Mono<HttpResponse<?>> resetInventory();

    @Get("/{id}")
    @Operation(summary = "get single productx",
        description = "get single product"
    )
    @ApiResponse(responseCode = "400", description = "invalid product request")
    @ApiResponse(responseCode = "200", description = "found products")
    @ApiResponse(responseCode = "404", description = "product not found")
    @Tag(name = "product")
    Mono<Product> getProduct(UUID id);

}
