package com.excalibur.product.event;

import io.micronaut.http.client.annotation.Client;

@Client("/events")
public interface ProductEventClient extends ProductEventApi {
}
