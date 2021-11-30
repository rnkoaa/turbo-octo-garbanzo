package com.excalibur

import com.excalibur.functest.ProductClient
import com.excalibur.product.Product
import com.excalibur.product.ProductVariant
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import spock.lang.Shared

@MicronautTest(environments = ["test"], packages = ["com.excalibur.functest"])
class ProductSpecification extends ApplicationContextSpecification {

    @Shared
    ProductClient productClient = applicationContext.createBean(ProductClient)

    def "client is wired"() {
        expect:
        applicationContext != null
        productClient != null
    }

    def cleanup() {
        productClient.resetProducts().block()
    }

    def "can create new product"() {
        given:
        Product product = new Product(UUID.randomUUID(), "DDD distilled", new ProductVariant(UUID.randomUUID(), "ebook"))

        when:
        Product response = productClient.create(product).block()

        then:
        response != null
        response == product

        when:
        List<Product> products = productClient.getProducts().block()

        then:
        products.size() == 7

    }


    def "check health of client"() {
        when:
        List<Product> res = productClient.getProducts().block()

        then:
        res.size() == 6
    }
}
