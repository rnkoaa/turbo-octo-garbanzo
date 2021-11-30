package com.excalibur

import com.excalibur.functest.ProductClient
import com.excalibur.product.Product
import com.excalibur.product.ProductVariant
import spock.lang.Shared

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

    def "retrieve all products"() {

        when:
        List<Product> products = productClient.getProducts().block()

        then:
        products.size() == 6
    }

    def "retrieve one product that exists"() {

        when:
        List<Product> products = productClient.getProducts().block()

        then:
        products.size() == 6

        when:
        Product product = products[0]
        Product foundProduct = productClient.getProduct(product.id()).block()

        then:
        foundProduct != null
        product == foundProduct
    }

    def "deleting existing products work"() {

        when:
        List<Product> products = productClient.getProducts().block()

        then:
        products.size() == 6

        when:
        Product product = products[0]
        productClient.deleteProduct(product.id()).block()
        List<Product> newProducts = productClient.getProducts().block()

        then:
        noExceptionThrown()
        newProducts.size() == 5

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
}
