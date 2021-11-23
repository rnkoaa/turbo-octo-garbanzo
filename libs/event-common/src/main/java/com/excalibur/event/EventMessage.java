package com.excalibur.event;

import com.excalibur.event.annotations.AggregateEvent;
import com.excalibur.event.jackson.serde.EventIdValueFinder;
import java.time.Instant;
import java.util.Optional;
import org.immutables.value.Value.Immutable;

@Immutable
@EventStyle
public interface EventMessage {

    EventMetadata getMetadata();

    Event getEvent();

    static Builder newBuilder() {
        return new Builder();
    }

    static EventMessage of(Event event) {
        Optional<String> maybeEventId = EventIdValueFinder.findEventId(event.getClass(), AggregateEvent.class);
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

    class Builder extends EventMessageImpl.Builder {}
}
