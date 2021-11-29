package com.excalibur.functest;

import io.micronaut.context.annotation.ConfigurationProperties;

@ConfigurationProperties(value = "application")
public class ClientConfig {
    private String server;
    private int port;

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getApplicationURL() {
        return "%s:%d".formatted(server, port);
    }
}
