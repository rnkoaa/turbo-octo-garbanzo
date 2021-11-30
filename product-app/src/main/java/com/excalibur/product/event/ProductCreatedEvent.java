package com.excalibur.product.event;

import com.excalibur.event.VersionedEvent;
import com.excalibur.event.annotations.AggregateEvent;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.Optional;
import java.util.UUID;
import org.immutables.value.Value.Immutable;

@AggregateEvent
@ExcaliburStyle
@Immutable
@JsonDeserialize(builder = ProductCreatedEvent.Builder.class)
public interface ProductCreatedEvent extends VersionedEvent {

    Optional<UUID> getProductId();

    String getName();

    Optional<String> getDescription();

    static Builder newBuilder() {
        return new Builder();
    }

   class Builder extends ProductCreatedEventImpl.Builder {}
}
