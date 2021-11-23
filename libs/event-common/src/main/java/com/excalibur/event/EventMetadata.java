package com.excalibur.event;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import org.immutables.value.Value.Default;
import org.immutables.value.Value.Immutable;

@Immutable
@EventStyle
@JsonDeserialize(builder = EventMetadata.Builder.class)
public interface EventMetadata {

    String getType();

    UUID getId();

    Optional<String> getSource();

    @Default
    default Instant getTimestamp() {
        return Instant.now();
    }

    static Builder newBuilder() {
        return new Builder();
    }

    class Builder extends EventMetadataImpl.Builder {}

}
