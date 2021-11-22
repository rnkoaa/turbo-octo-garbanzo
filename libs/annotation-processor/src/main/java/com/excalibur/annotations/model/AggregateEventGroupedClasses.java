package com.excalibur.annotations.model;

import static com.excalibur.annotations.model.JavaPoet.classOfAny;
import static javax.lang.model.element.Modifier.PUBLIC;

import com.excalibur.event.annotations.AggregateEvent;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

public class AggregateEventGroupedClasses {

    private final Map<String, AggregateEventAnnotatedClass> itemsMap = new LinkedHashMap<>();
    private static final String EVENT_CLASS_CACHE = "eventClassCache";
    protected final static String CLASS_NAME = "AggregateEventClassCache";
    private final String packageName = "com.excalibur";

    public void add(AggregateEventAnnotatedClass toInsert) throws ProcessingException {
        AggregateEventAnnotatedClass existing = itemsMap.get(toInsert.getId());
        if (existing != null) {

            // Already existing
            throw new ProcessingException(toInsert.getElement(),
                "Conflict: The class %s is annotated with @%s with id ='%s' but %s already uses the same id",
                toInsert.getElement().getQualifiedName().toString(),
                AggregateEvent.class.getSimpleName(),
                toInsert.getId(), existing.getElement().getQualifiedName().toString());
        }

        itemsMap.put(toInsert.getId(), toInsert);
    }

    public int size() {
        return itemsMap.size();
    }

    public void clear() {
        itemsMap.clear();
    }

    public void generateCode(Types typeUtils, Filer filer) throws IllegalArgumentException, IOException {
        List<AggregateEventAnnotatedClass> handlerInfos = itemsMap.values().stream().toList();
        TypeSpec.Builder typeSpecBuilder = TypeSpec.classBuilder(CLASS_NAME)
            .addModifiers(PUBLIC, Modifier.FINAL)
            .addStaticBlock(generateStaticInitializer(typeUtils, handlerInfos))
            .addField(JavaPoet.generateEventsApplierMapField(EVENT_CLASS_CACHE))
            .addMethod(generateFindMethod());

        JavaFile javaFile = JavaFile.builder(packageName, typeSpecBuilder.build())
            .build();
        javaFile.writeTo(filer);

    }

    private MethodSpec generateFindMethod() {
        return MethodSpec.methodBuilder("findEventClass")
            .addParameter(ClassName.get(String.class), "eventClass")
            .addModifiers(Modifier.PRIVATE)
            .returns(ParameterizedTypeName.get(
                ClassName.get(Optional.class),
                classOfAny()
            ))
            .addStatement(
                "return $T.ofNullable($L.get(eventClass))",
                Optional.class, EVENT_CLASS_CACHE
            )
            .build();
    }

    private CodeBlock generateStaticInitializer(Types typeUtils, List<AggregateEventAnnotatedClass> handlerInfos) {
        CodeBlock.Builder entryBuilder = CodeBlock.builder()
            .add("$L = Map.ofEntries(", EVENT_CLASS_CACHE);
        String baseFormat = "\n\t$T.entry($S, $T.class)";
        for (int index = 0; index < handlerInfos.size(); index++) {
            var eventHandlerInfo = handlerInfos.get(index);
            TypeMirror beanClassTypeMirror = eventHandlerInfo.getElement().asType();
            if (beanClassTypeMirror != null) {
                TypeElement eventElement = (TypeElement) typeUtils.asElement(beanClassTypeMirror);
                String eventClassName = ClassName.get(eventElement).simpleName();
                // if not the last item
                if (index != handlerInfos.size() - 1) {
                    entryBuilder.add(baseFormat + ",",
                        Map.class,
                        eventClassName,
                        eventHandlerInfo.getElement()
                    );
                } else {
                    entryBuilder.add(baseFormat,
                        Map.class,
                        eventClassName,
                        eventHandlerInfo.getElement()
                    );
                }
            }
        }

        entryBuilder
            .add("\n);\n");
        return entryBuilder.build();
    }

    //
//    public Optional<Class<? extends Event>> get(String key) {
//        return Optional.ofNullable(eventClassCache.get(key));
//    }

}
