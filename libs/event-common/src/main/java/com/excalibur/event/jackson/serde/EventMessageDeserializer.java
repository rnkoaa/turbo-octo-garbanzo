package com.excalibur.event.jackson.serde;

import static com.excalibur.event.jackson.serde.EventMessageSerializer.EVENT_DATA_KEY;
import static com.excalibur.event.jackson.serde.EventMessageSerializer.EVENT_METADATA_KEY;

import com.excalibur.event.Event;
import com.excalibur.event.EventClassCache;
import com.excalibur.event.EventMessage;
import com.excalibur.event.EventMetadata;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Optional;

public class EventMessageDeserializer extends JsonDeserializer<EventMessage> {

    private static final EventClassCache eventClassCache = EventClassCache.getInstance();

    @Override
    public EventMessage deserialize(JsonParser jp, DeserializationContext ctxt)
        throws IOException {
        ObjectMapper objectMapper = (ObjectMapper) jp.getCodec();
        JsonNode node = jp.getCodec().readTree(jp);
        TreeNode metadata = node.get(EVENT_METADATA_KEY);
        if (metadata == null) {
            throw new IllegalStateException("metadata object required to be deserialized");
        }

        JsonNode type = (JsonNode) metadata.get("type");
        if (type == null) {
            throw new IllegalStateException("event type object required to be deserialized");
        }

        String typeValue = type.textValue();

        Optional<Class<? extends Event>> maybeClzz = eventClassCache.get(typeValue);
        Class<? extends Event> clzz = maybeClzz.orElseThrow(() -> new AggregateEventClassNotFoundException(typeValue));

        JsonParser eventNode = node.get(EVENT_DATA_KEY).traverse();
        Event event = objectMapper.readValue(eventNode, clzz);
        EventMetadata eventMetadata = objectMapper.readValue(metadata.traverse(), EventMetadata.class);

        return EventMessage.newBuilder()
            .event(event)
            .metadata(eventMetadata)
            .build();
    }
}
