package com.excalibur.product.event;

import com.excalibur.event.EventMessage;
import com.excalibur.product.data.ProductEvents;
import io.micronaut.http.annotation.Controller;
import java.util.List;
import java.util.UUID;
import reactor.core.publisher.Mono;

@Controller("/events")
public class ProductEventController implements ProductEventApi {

    static ProductEvents productEvents = ProductEvents.getInstance();

    @Override
    public Mono<EventMessage> get(UUID id) {
        EventMessage eventMessage = productEvents.get(id);
        if (eventMessage != null) {
            return Mono.just(eventMessage);
        }
        return Mono.error(new NotFoundException("id '" + id + "' does not exist"));
    }

    @Override
    public Mono<List<EventMessage>> get() {
        return Mono.just(productEvents.getAll());
    }

    static class NotFoundException extends RuntimeException {

        public NotFoundException(String message) {
            super(message);
        }
    }
}
