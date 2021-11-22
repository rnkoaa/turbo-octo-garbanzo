package com.excalibur.event;

import java.time.Instant;
import java.util.UUID;
import org.immutables.value.Value.Default;

public interface Event {

    @Default
    default UUID getId() {
        return UUID.randomUUID();
    }

    @Default
    default Instant getCreated() {
        return Instant.now();
    }
}
