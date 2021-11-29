package com.excalibur.product.event;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.excalibur.ObjectMapperProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class ProductSerDeTest {

    private final ObjectMapper objectMapper = ObjectMapperProvider.getInstance().getObjectMapper();

    @Test
    void serializeProductCreatedEvent() throws JsonProcessingException {
        var productCreatedEvent = ProductCreatedEvent.newBuilder()
            .name("Hane's T-shirts")
            .build();

        String event = objectMapper.writeValueAsString(productCreatedEvent);
        System.out.println(event);

    }

    @Test
    void productCreatedEventCanBeDeserialized() throws JsonProcessingException {
        String productCreatedJson = """
            {"id":"eb5df500-cd6a-4a22-ab77-a1561057f13d","created":"2021-11-21T19:15:34.939873Z","version":1,"name":"Hane's T-shirts"}
            """;

        var productCreatedEvent = objectMapper.readValue(productCreatedJson, ProductCreatedEvent.class);
        assertThat(productCreatedEvent).isNotNull();
        assertThat(productCreatedEvent).extracting(ProductCreatedEvent::getId, ProductCreatedEvent::getName)
            .contains(UUID.fromString("eb5df500-cd6a-4a22-ab77-a1561057f13d"), "Hane's T-shirts");
    }

    @Test
    void serializeProductUpdatedEvent() throws JsonProcessingException {
        var productCreatedEvent = ProductUpdatedEvent.newBuilder()
            .productId(UUID.randomUUID())
            .name("Hane's T-shirts")
            .description("white T-shirt")
            .build();

        String event = objectMapper.writeValueAsString(productCreatedEvent);
        System.out.println(event);

    }

    @Test
    void productUpdatedEventCanBeDeserialized() throws JsonProcessingException {
        String productUpdatedEvent = """
            {"id":"3ac4d9fd-ad9d-420e-9747-bc675b606f10","created":"2021-11-21T19:25:15.285176Z","version":1,"product_id":"d0813f04-2e85-4007-99df-213f196ff02b","name":"Hane's T-shirts","description":"white T-shirt"}
            """;

        var event = objectMapper.readValue(productUpdatedEvent, ProductUpdatedEvent.class);
        assertThat(event).isNotNull();
        assertThat(event)
            .extracting(
                ProductUpdatedEvent::getProductId,
                ProductUpdatedEvent::getId,
                ProductUpdatedEvent::getName,
                ProductUpdatedEvent::getDescription
            )
            .contains(
                UUID.fromString("d0813f04-2e85-4007-99df-213f196ff02b"),
                UUID.fromString("3ac4d9fd-ad9d-420e-9747-bc675b606f10"),
                Optional.of("Hane's T-shirts"),
                Optional.of("white T-shirt")
            );
    }
}
