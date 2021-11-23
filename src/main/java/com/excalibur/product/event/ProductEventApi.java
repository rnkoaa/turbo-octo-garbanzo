package com.excalibur.product.event;

import com.excalibur.event.EventMessage;
import io.micronaut.http.annotation.Get;
import java.util.List;
import java.util.UUID;
import reactor.core.publisher.Mono;

public interface ProductEventApi {

    @Get("/{id}")
    Mono<EventMessage> get(UUID id);

    @Get("/all")
    Mono<List<EventMessage>> get();
}
