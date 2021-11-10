package com.richard;

import io.micronaut.core.annotation.Introspected;

import java.util.UUID;

@Introspected
public record ProductVariant (UUID id, String name){
}
