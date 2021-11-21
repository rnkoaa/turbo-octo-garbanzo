package com.richard;

import io.micronaut.http.client.annotation.Client;

@Client("/product")
public interface ProductIntegrationClient extends ProductApi{
}
