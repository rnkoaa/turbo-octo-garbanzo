package com.richard;

import java.util.UUID;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller("/variants")
public class ProductVariantApi {
    private Products products = Products.getInstance();

    @Get("/{productId}/variant/{variantId}")
    ProductVariant getVariant(UUID productId, UUID variantId) {
        Product product = products.get(productId);
        if (product != null) {
            return product.variant();
        }
        return null;
    }
}
