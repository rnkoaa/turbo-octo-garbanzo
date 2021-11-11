package com.richard;

import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

@Introspected
@Schema(name = "Product", description = "Product schema")
public record Product(UUID id, String name, ProductVariant variant) {}
