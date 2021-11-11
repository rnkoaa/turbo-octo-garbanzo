package com.richard;

import io.micronaut.core.annotation.Introspected;
import java.util.UUID;

@Introspected
public record Product(UUID id, String name, ProductVariant variant) {}
