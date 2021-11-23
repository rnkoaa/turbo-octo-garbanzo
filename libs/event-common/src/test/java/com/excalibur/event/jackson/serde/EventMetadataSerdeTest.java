package com.excalibur.event.jackson.serde;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.excalibur.event.EventMessage;
import com.excalibur.event.test.ItemCreatedEventBuilder;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class EventMetadataSerdeTest {

    private final ObjectMapper objectMapper = buildObjectMapper();

    @Test
    void testSerialization() throws JsonProcessingException {
        var item = new ItemCreatedEventBuilder().name("Hane's White T-shirt")
            .created(Instant.now())
            .id(UUID.randomUUID())
            .version(1L)
            .build();
        var eventMessage = EventMessage.of(item);
        var eventMessageValue = objectMapper.writeValueAsString(eventMessage);
        assertThat(eventMessageValue).isNotNull().isNotEmpty()
            .contains("Hane's White T-shirt");

    }

    @Test
    void testDeserializationWithoutCache() throws JsonProcessingException {
        var item = new ItemCreatedEventBuilder().name("Hane's White T-shirt")
            .created(Instant.now())
            .id(UUID.randomUUID())
            .version(1L)
            .build();
        var eventMessage = EventMessage.of(item);

        var eventMessageValue = objectMapper.writeValueAsString(eventMessage);
        assertThat(eventMessageValue).isNotNull().isNotEmpty();

        EventMessage deserializedEventMessage = objectMapper.readValue(eventMessageValue, EventMessage.class);
        assertThat(deserializedEventMessage).isEqualTo(eventMessage);
    }

    private ObjectMapper buildObjectMapper() {
        var objectMapper = new ObjectMapper().findAndRegisterModules();
        objectMapper.setPropertyNamingStrategy(new PropertyNamingStrategies.SnakeCaseStrategy());

        // Ignore null values when writing json.
        objectMapper.setSerializationInclusion(Include.NON_DEFAULT);
        objectMapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);

        // Write times as a String instead of a Long so its human readable.
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(new EventMessageModule());

        return objectMapper;
    }
}
