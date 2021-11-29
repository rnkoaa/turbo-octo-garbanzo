package com.excalibur

import io.micronaut.core.type.Argument
import io.micronaut.http.client.HttpClient
import spock.lang.AutoCleanup
import spock.lang.Shared

class ProductSpecification extends ApplicationContextSpecification {
//    @Shared
//    @AutoCleanup
//    EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer)

    @Shared
    @AutoCleanup
    HttpClient httpClient = applicationContext.createBean(HttpClient)


    def "client is wired"() {
        expect:
        applicationContext != null
        httpClient != null
    }

    def "check health of client"() {

        expect:
        String appUrl = applicationContext.getRequiredProperty("application.server", String.class)
        String port = applicationContext.getRequiredProperty("application.port", String.class)

        println "http:$appUrl:$port"

        applicationContext != null
        httpClient != null

    }
}
