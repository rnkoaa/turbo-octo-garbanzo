package com.excalibur.event;

import com.excalibur.event.jackson.serde.EventMessageDeserializer;
import com.excalibur.event.jackson.serde.EventMessageSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value.Immutable;

@Immutable
@EventStyle
@JsonSerialize(using = EventMessageSerializer.class)
@JsonDeserialize(using = EventMessageDeserializer.class)
//@JsonDeserialize(builder = EventMessage.Builder.class)
public interface EventMessage {

    EventMetadata getMetadata();

    Event getEvent();

    static Builder newBuilder() {
        return new Builder();
    }

    class Builder extends EventMessageImpl.Builder {}
}
