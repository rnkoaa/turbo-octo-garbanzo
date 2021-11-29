package com.excalibur

import io.micronaut.context.ApplicationContext
import io.micronaut.http.client.BlockingHttpClient
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

abstract class ApplicationContextSpecification extends Specification
        implements ConfigurationFixture {

    @AutoCleanup
    @Shared
    ApplicationContext applicationContext = ApplicationContext.run(configuration)

//    @AutoCleanup
//    @Shared
//    EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer, configuration)
//    @AutoCleanup
//    @Shared
//    ApplicationContext applicationContext = embeddedServer.applicationContext
//    @AutoCleanup
//    @Shared
//    HttpClient httpClient = applicationContext.createBean(HttpClient, embeddedServer.URL)
    BlockingHttpClient getClient() {

//        httpClient.toBlocking()
    }
//
//    def cleanup() {
//        assert !hasLeakage()
//    }
}
