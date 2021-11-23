package com.excalibur.event;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EventClassCache {

    private static final EventClassCache instance = new EventClassCache();

    private static final String EVENT_CACHE_PATH = "META-INF/events/com.excalibur.AggregateEventReference";
    private final Map<String, Class<? extends Event>> events = new HashMap<>();

    private EventClassCache() {
        loadCache();
    }

    private void loadCache() {
        ClassLoader classLoader = EventClassCache.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(EVENT_CACHE_PATH);
        if (inputStream != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                reader.lines()
                    .map(it -> it.split(","))
                    .filter(it -> it.length == 2)
                    .forEach(it -> {
                        try {
                            @SuppressWarnings("unchecked")
                            Class<? extends Event> eventClzz = (Class<? extends Event>) classLoader.loadClass(it[1]);
                            events.put(it[0], eventClzz);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

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
