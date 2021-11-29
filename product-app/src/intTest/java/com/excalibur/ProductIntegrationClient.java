package com.excalibur;

import com.excalibur.product.ProductApi;
import io.micronaut.http.client.annotation.Client;

@Client("/product")
public interface ProductIntegrationClient extends ProductApi {
}
