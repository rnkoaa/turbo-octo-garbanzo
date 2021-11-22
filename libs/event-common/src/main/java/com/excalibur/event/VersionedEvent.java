package com.excalibur.event;

import org.immutables.value.Value.Default;

public interface VersionedEvent extends Event {

    @Default
    default Long getVersion() {
        return 1L;
    }
}
