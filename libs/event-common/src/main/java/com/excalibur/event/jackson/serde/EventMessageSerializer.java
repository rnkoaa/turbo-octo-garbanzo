package com.excalibur.event.jackson.serde;

import com.excalibur.event.EventClassCache;
import com.excalibur.event.EventMessage;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

public class EventMessageSerializer extends JsonSerializer<EventMessage> {

    public static final String EVENT_METADATA_KEY = "metadata";
    public static final String EVENT_DATA_KEY = "data";
    private static final EventClassCache eventClassCache = EventClassCache.getInstance();

    @Override
    public void serialize(EventMessage value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        eventClassCache.put(value.getEvent().getClass());
        gen.writeStartObject();
        gen.writeObjectField(EVENT_METADATA_KEY, value.getMetadata());
        System.out.println(value.getEvent().toString());
        gen.writeObjectField(EVENT_DATA_KEY, value.getEvent());

        gen.writeEndObject();
    }


}
