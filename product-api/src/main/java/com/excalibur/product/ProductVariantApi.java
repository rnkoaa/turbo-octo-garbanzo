package com.excalibur.product;

import io.micronaut.http.annotation.Get;
import java.util.UUID;

public interface ProductVariantApi {

    @Get("/{productId}/variant/{variantId}")
    ProductVariant getVariant(UUID productId, UUID variantId);
}
