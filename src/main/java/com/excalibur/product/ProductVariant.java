package com.excalibur.product;

import io.micronaut.core.annotation.Introspected;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

@Introspected
@Schema(name = "ProductVariant", description = "Product Variant schema")
public record ProductVariant (UUID id, String name){
}
