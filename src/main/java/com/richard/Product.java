package com.richard;

import java.util.UUID;

public record Product(UUID id, String name, ProductVariant variant) {}
