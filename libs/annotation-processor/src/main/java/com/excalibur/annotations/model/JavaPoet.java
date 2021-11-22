package com.excalibur.annotations.model;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.WildcardTypeName;
import java.util.Map;
import javax.lang.model.element.Modifier;

public class JavaPoet {

    protected static FieldSpec generateEventsApplierMapField(String fieldName) {
        return FieldSpec.builder(
                ParameterizedTypeName.get(
                    ClassName.get(Map.class),
                    ClassName.get(String.class),
                    classOfAny()
                ),
                fieldName
            )
            .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
            .build();
    }

    protected static ParameterizedTypeName classOfAny() {
        TypeName wildcard = WildcardTypeName.subtypeOf(Object.class);

        return ParameterizedTypeName.get(
            ClassName.get(Class.class), wildcard);
    }

}
