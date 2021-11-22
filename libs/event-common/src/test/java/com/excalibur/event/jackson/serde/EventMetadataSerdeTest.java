package com.excalibur.event.jackson.serde;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.excalibur.event.Event;
import com.excalibur.event.EventMessage;
import com.excalibur.event.EventMetadata;
import com.excalibur.event.annotations.AggregateEvent;
import com.excalibur.event.test.ItemCreatedEventBuilder;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class EventMetadataSerdeTest {

    private final ObjectMapper objectMapper = buildObjectMapper();
    private final EventIdValueFinder eventIdValueFinder = new EventIdValueFinder();

    @Test
    void testSerialization() throws JsonProcessingException {

        var item = new ItemCreatedEventBuilder().name("Hane's White T-shirt")
            .created(Instant.now())
            .id(UUID.randomUUID())
            .version(1L)
            .build();
        var eventMessage = create(item);
//
        var eventMessageValue = objectMapper.writeValueAsString(eventMessage);
        assertThat(eventMessageValue).isNotNull().isNotEmpty();
        System.out.println(eventMessageValue);

    }

    @Test
    void testDeserializationWithoutCache() throws JsonProcessingException {
        var item = new ItemCreatedEventBuilder().name("Hane's White T-shirt")
            .created(Instant.now())
            .id(UUID.randomUUID())
            .version(1L)
            .build();
        var eventMessage = create(item);

        var eventMessageValue = objectMapper.writeValueAsString(eventMessage);
        assertThat(eventMessageValue).isNotNull().isNotEmpty();

        EventMessage deserializedEventMessage = objectMapper.readValue(eventMessageValue, EventMessage.class);
        assertThat(deserializedEventMessage).isEqualTo(eventMessage);
    }

    public EventMessage create(Event event) {
        Optional<String> maybeEventId = eventIdValueFinder.findEventId(event.getClass(), AggregateEvent.class);
        if (maybeEventId.isEmpty() || maybeEventId.get().isEmpty()) {
            return null;
        }

        var metadata = EventMetadata.newBuilder()
            .id(event.getId())
            .type(maybeEventId.orElse(""))
            .timestamp(Instant.now())
            .build();

        return EventMessage.newBuilder()
            .metadata(metadata)
            .event(event)
            .build();

    }

    private ObjectMapper buildObjectMapper() {
        var objectMapper = new ObjectMapper().findAndRegisterModules();
        objectMapper.setPropertyNamingStrategy(new PropertyNamingStrategies.SnakeCaseStrategy());

        // Ignore null values when writing json.
        objectMapper.setSerializationInclusion(Include.NON_DEFAULT);
        objectMapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);
//        objectMapper.configure(SerializationFeature., true);

        // Write times as a String instead of a Long so its human readable.
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        objectMapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE)
        objectMapper.registerModule(new JavaTimeModule());
//        objectMapper.registerModule(new Jdk8Module())

        return objectMapper;
    }
}
