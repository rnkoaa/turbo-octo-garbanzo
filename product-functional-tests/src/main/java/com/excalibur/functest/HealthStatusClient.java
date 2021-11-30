package com.excalibur.functest;

import reactor.core.publisher.Mono;
import retrofit2.http.GET;

public interface HealthStatusClient {

    @GET("/health")
    Mono<HealthStatus> health();
}
