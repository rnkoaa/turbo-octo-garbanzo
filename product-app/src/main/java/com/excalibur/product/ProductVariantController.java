package com.excalibur.product;

import com.excalibur.product.Product;
import com.excalibur.product.ProductVariant;
import com.excalibur.product.ProductVariantApi;
import com.excalibur.product.data.Products;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import java.util.UUID;

@Controller("/variants")
public class ProductVariantController implements ProductVariantApi {

    private final Products products = Products.getInstance();

    @Get("/{productId}/variant/{variantId}")
    public ProductVariant getVariant(UUID productId, UUID variantId) {
        Product product = products.get(productId);
        if (product != null) {
            return product.variant();
        }
        return null;
    }
}
