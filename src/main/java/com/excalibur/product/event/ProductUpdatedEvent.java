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
@JsonDeserialize(builder = ProductUpdatedEvent.Builder.class)
public interface ProductUpdatedEvent extends VersionedEvent {

    UUID getProductId();

    Optional<String> getName();

    Optional<String> getDescription();

    static ProductUpdatedEvent.Builder newBuilder() {
        return new ProductUpdatedEvent.Builder();
    }

    class Builder extends ProductUpdatedEventImpl.Builder {}
}
