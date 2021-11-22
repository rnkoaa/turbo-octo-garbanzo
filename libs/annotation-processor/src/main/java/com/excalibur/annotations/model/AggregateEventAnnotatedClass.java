package com.excalibur.annotations.model;

import javax.lang.model.element.TypeElement;

public class AggregateEventAnnotatedClass {

    private final TypeElement element;

    public AggregateEventAnnotatedClass(TypeElement element) {
        this.element = element;
    }

    public String getId() {
        return element.getSimpleName().toString();
    }

    public TypeElement getElement() {
        return element;
    }

}
