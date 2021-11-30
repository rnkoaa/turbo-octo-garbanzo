package com.excalibur

import com.excalibur.functest.HealthStatus
import com.excalibur.functest.HealthStatusClient
import spock.lang.Shared

class HealthStatusSpec extends ApplicationContextSpecification {

    @Shared
    HealthStatusClient healthStatusClient = applicationContext.createBean(HealthStatusClient)

    def "context loads"() {
        expect:
        healthStatusClient != null
    }

    def "retrieve health info"() {
        when:
        HealthStatus healthStatus = healthStatusClient.health().block()

        then:
        healthStatus != null
        healthStatus.status == "UP"
    }
}
