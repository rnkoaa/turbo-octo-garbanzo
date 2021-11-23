package com.excalibur.event.jackson.serde;

import com.excalibur.event.EventMessage;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class EventMessageModule extends SimpleModule {

    private static final String NAME = "EventMessageModule";

    public EventMessageModule() {
        super(NAME);
        addSerializer(EventMessage.class, new EventMessageSerializer());
        addDeserializer(EventMessage.class, new EventMessageDeserializer());
    }
}
