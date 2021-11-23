package com.excalibur.annotations.processing;

import com.excalibur.annotations.model.AggregateEventAnnotatedClass;
import com.excalibur.annotations.model.AggregateEventGroupedClasses;
import com.excalibur.annotations.model.ProcessingException;
import com.excalibur.event.annotations.AggregateEvent;
import com.google.auto.service.AutoService;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

@AutoService(Processor.class)
public class AggregateEventProcessor extends AbstractAnnotationProcessor {

    AggregateEventGroupedClasses aggregateEvents = new AggregateEventGroupedClasses();

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(AggregateEvent.class);
        Optional<? extends Element> any = elementsAnnotatedWith.stream()
            .filter(it -> !(it.getKind() == ElementKind.CLASS || it.getKind() == ElementKind.INTERFACE))
            .findAny();
        if (any.isPresent()) {
            Element element = any.get();
            note("element " + element.getKind().toString());
            error(element, " Fix element annotated with @AggregateEvent");

            return true;
        }

        elementsAnnotatedWith
            .stream()
            .map(it -> (TypeElement) it)
            .forEach(it -> {
                try {
                    aggregateEvents.add(new AggregateEventAnnotatedClass(it));
                } catch (ProcessingException e) {
                    e.printStackTrace();
                }
            });

        note("found %d events to process", elementsAnnotatedWith.size());
        if (aggregateEvents.size() > 0) {
            try {
                aggregateEvents.writeIndexFile(processingEnv.getFiler(), "com.excalibur.AggregateEventReference");
                aggregateEvents.clear();
            } catch (IOException e) {
                error("error while writing index file with message " + e.getMessage());
                return true;
            }
        }
        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of(AggregateEvent.class.getCanonicalName());
    }
}
