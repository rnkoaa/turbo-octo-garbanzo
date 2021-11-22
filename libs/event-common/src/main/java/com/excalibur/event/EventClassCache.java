package com.excalibur.event;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EventClassCache {

    private static final EventClassCache instance = new EventClassCache();
    //    private static final Map<String, Class<? extends Event>> events = Map.ofEntries(
//        Map.entry("ProductCreatedEvent", ProductCreatedEvent.class),
//        Map.entry("ProductUpdatedEvent", ProductUpdatedEvent.class),
//        Map.entry("ProductDeactivatedEvent", ProductDeactivatedEvent.class)
//    );
    private static final Map<String, Class<? extends Event>> events = new HashMap<>();

    public static EventClassCache getInstance() {
        return instance;
    }

    public void put(Class<? extends Event> clzz) {
        events.put(clzz.getSimpleName(), clzz);
    }

    public Optional<Class<? extends Event>> get(String eventName) {
        return Optional.ofNullable(events.get(eventName));
    }
}
