package com.excalibur

import com.excalibur.functest.ClientConfig
import com.excalibur.functest.RetrofitFactory
import io.micronaut.context.ApplicationContext
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

abstract class ApplicationContextSpecification extends Specification implements ConfigurationFixture {

    @AutoCleanup
    @Shared
    ApplicationContext applicationContext = ApplicationContext.run(configuration)

    BlockingHttpClient getClient() {
        ClientConfig clientConfig = applicationContext.createBean(ClientConfig)
        URL url = new URL(clientConfig.applicationURL)

        return applicationContext.createBean(HttpClient, url).toBlocking()
    }
//
//    def cleanup() {
//        assert !hasLeakage()
//    }
}
