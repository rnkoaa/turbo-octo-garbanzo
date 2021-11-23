package com.excalibur.product.data;

import com.excalibur.event.Event;
import com.excalibur.event.EventMessage;
import com.excalibur.product.event.ProductCreatedEvent;
import com.excalibur.product.event.ProductUpdatedEvent;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ProductEvents {

    private static final Map<UUID, EventMessage> events = new LinkedHashMap<>();
    private static final ProductEvents instance = new ProductEvents();

    private static void addEvent(Event event) {
        var eventMessage = EventMessage.of(event);
        events.put(event.getId(), eventMessage);
    }

    private static void addSampleProductEvents() {
        addEvent(ProductCreatedEvent.newBuilder().productId(UUID.randomUUID()).name("Black Product 1").build());
        addEvent(ProductCreatedEvent.newBuilder().productId(UUID.randomUUID()).name("Blue Product 4").build());
        addEvent(ProductCreatedEvent.newBuilder().productId(UUID.randomUUID()).name("Purple Product 2").build());
        addEvent(ProductUpdatedEvent.newBuilder()
            .description("Chic Purple dress")
            .productId(UUID.randomUUID()).name("Purple Product 2").build());
        addEvent(ProductUpdatedEvent.newBuilder().productId(UUID.randomUUID()).name("Green Product 3").build());
        addEvent(ProductCreatedEvent.newBuilder().productId(UUID.randomUUID()).name("Red Product 6").build());
        addEvent(ProductUpdatedEvent.newBuilder().productId(UUID.randomUUID())
            .description("Yellow body shaping dress")
            .name("Yellow Product 3").build());
        addEvent(ProductCreatedEvent.newBuilder().productId(UUID.randomUUID()).name("Orange Product 6").build());
    }

    static {
        addSampleProductEvents();
    }

    public static ProductEvents getInstance() {
        return instance;
    }

    public EventMessage get(UUID id) {
        return events.get(id);
    }

    public List<EventMessage> getAll() {
        return events.values().stream().toList();
    }

    public void add(Event event) {
        addEvent(event);
    }

    public void reset() {
        events.clear();
        addSampleProductEvents();
    }
}
