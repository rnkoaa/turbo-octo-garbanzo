package com.excalibur.functest;

import io.micronaut.core.annotation.Introspected;

@Introspected
public class HealthStatus {
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
