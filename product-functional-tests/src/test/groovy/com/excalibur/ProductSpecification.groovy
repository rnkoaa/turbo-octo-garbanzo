package com.excalibur


import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.test.extensions.spock.annotation.MicronautTest

@MicronautTest(environments = ["test"], packages = ["com.excalibur.functest"])
class ProductSpecification extends ApplicationContextSpecification {

    def "client is wired"() {
        expect:
        applicationContext != null
    }


    def "check health of client"() {
        given:
        def httpRequest = HttpRequest.GET("/health")

        when:
        HttpResponse<String> res = getClient().exchange(httpRequest, Argument.of(String))

        then:
        res.status() == HttpStatus.OK
        assert res.body.isPresent()

        when:
        String body = res.body()

        then:
        assert body == "{\"status\":\"UP\"}"
    }
}
