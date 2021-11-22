package com.excalibur.event.test;

import com.excalibur.event.VersionedEvent;
import com.excalibur.event.annotations.AggregateEvent;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import org.immutables.builder.Builder;
import org.immutables.value.Value;

@AggregateEvent
//@Value.Style(newBuilder = "newBuilder")
@JsonDeserialize(builder = ItemCreatedEventBuilder.class)
public class ItemCreatedEvent implements VersionedEvent {

    private final String name;
    private final UUID id;
    private final Long version;
    private final Instant created;

    @Builder.Constructor
    public ItemCreatedEvent(UUID id, Long version, String name, Instant created) {
        this.name = name;
        this.id = id == null ? UUID.randomUUID() : id;
        this.version = version != null ? version : 1L;
        this.created = created != null ? created : Instant.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ItemCreatedEvent that = (ItemCreatedEvent) o;
        return Objects.equals(name, that.name) && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }

    @Override
    public Long getVersion() {
        return version;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public Instant getCreated() {
        return created;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ItemCreatedEvent{" +
            "name='" + name + '\'' +
            ", id=" + id +
            ", version=" + version +
            ", created=" + created +
            '}';
    }
}
