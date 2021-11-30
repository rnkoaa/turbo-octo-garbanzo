package com.excalibur.product;

import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

@Introspected
@Schema(name = "Product", description = "Product schema")
public record Product(UUID id, String name, ProductVariant variant) {

    public Product withName(String toUpdateName) {
        return new Product(id, toUpdateName, variant);
    }
}
