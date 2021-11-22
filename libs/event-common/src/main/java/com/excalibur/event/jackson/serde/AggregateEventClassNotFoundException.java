package com.excalibur.event.jackson.serde;

public class AggregateEventClassNotFoundException extends RuntimeException {

    public AggregateEventClassNotFoundException(String typeValue) {
        super("AggregateEvent class '" + typeValue + "' not found, ensure it is on the classpath");
    }
}
