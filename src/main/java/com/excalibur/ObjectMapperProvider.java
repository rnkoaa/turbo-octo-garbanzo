package com.excalibur;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ObjectMapperProvider {

    private final ObjectMapper objectMapper;
    private static final ObjectMapperProvider instance = new ObjectMapperProvider();

    private ObjectMapperProvider() {
        objectMapper = buildObjectMapper();
    }

    public static ObjectMapperProvider getInstance() {
        return instance;
    }

    private ObjectMapper buildObjectMapper() {
        var objectMapper = new ObjectMapper().findAndRegisterModules();
        objectMapper.setPropertyNamingStrategy(new PropertyNamingStrategies.SnakeCaseStrategy());

        // Ignore null values when writing json.
        objectMapper.setSerializationInclusion(Include.NON_DEFAULT);
        objectMapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);
//        objectMapper.configure(SerializationFeature., true);

        // Write times as a String instead of a Long so its human readable.
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        objectMapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE)
        objectMapper.registerModule(new JavaTimeModule());

        return objectMapper;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
