package com.excalibur.product.event;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.excalibur.event.Event;
import com.excalibur.event.EventMessage;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import java.util.List;
import org.junit.jupiter.api.Test;

@MicronautTest
class ProductEventControllerTest {

    @Inject
    private ProductEventClient productEventClient;

    @Test
    void retrieveAllEvents() {
        List<EventMessage> eventMessages = productEventClient.get().block();
        assertThat(eventMessages).isNotNull().hasSize(8);

        List<Event> events = eventMessages.stream()
            .map(EventMessage::getEvent)
            .toList();

        for (Event event : events) {
            System.out.println(event);
        }

    }

}