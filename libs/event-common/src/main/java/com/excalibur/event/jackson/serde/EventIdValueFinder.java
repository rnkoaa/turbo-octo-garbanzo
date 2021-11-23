package com.excalibur.event.jackson.serde;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class EventIdValueFinder {

    public static Optional<String> findEventId(Class<?> clzz, Class<? extends Annotation> annotatedClass) {
        if (clzz.isAnnotationPresent(annotatedClass)) {
            return Optional.of(clzz.getSimpleName());
        }

        Optional<Class<?>> annotatedInterface = findAnnotatedInterface(clzz, annotatedClass);
        return annotatedInterface.map(Class::getSimpleName)
            .or(() -> findAnnotatedSuperClass(clzz, annotatedClass)
                .map(Class::getSimpleName));

    }

    public static Optional<Class<?>> findAnnotatedInterface(Class<?> clzz, Class<? extends Annotation> annotatedClass) {
        Class<?>[] interfaces = clzz.getInterfaces();
        Optional<Class<?>> first = findInterfacesWithAnnotation(Arrays.asList(interfaces), annotatedClass);
        if (first.isPresent()) {
            return first;
        }

        List<Class<?>> allInterfaces = Arrays.stream(interfaces)
            .flatMap(it -> Arrays.stream(it.getInterfaces()))
            .toList();

        return findInterfacesWithAnnotation(allInterfaces, annotatedClass);

    }

    private static Optional<Class<?>> findInterfacesWithAnnotation(List<Class<?>> interfaces,
        Class<? extends Annotation> annotation) {
        return interfaces.stream()
            .filter(it -> it.isAnnotationPresent(annotation))
            .findFirst();
    }

    public  static Optional<Class<?>> findAnnotatedSuperClass(Class<?> clzz,
        Class<? extends Annotation> annotationClass) {
        Class<?> superclass = clzz.getSuperclass();
        if (superclass != null) {
            if (superclass.isAnnotationPresent(annotationClass)) {
                return Optional.of(superclass);
            }
            return findAnnotatedSuperClass(superclass, annotationClass);
        }
        return Optional.empty();
    }
}
