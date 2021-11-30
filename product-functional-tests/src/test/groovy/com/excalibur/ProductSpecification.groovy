package com.excalibur

import com.excalibur.functest.ProductClient
import com.excalibur.product.Product
import com.excalibur.product.ProductVariant
import retrofit2.HttpException
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

    def "retrieve one product that does not exist causes exception"() {

        when:
        productClient.getProduct(UUID.randomUUID()).blockOptional()

        then:
        thrown(HttpException)
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

    def "can update existing product"() {
        given:
        String newProductName = "DDD Distilled version 1"

        when:
        List<Product> products = productClient.getProducts().block()

        then:
        products.size() == 6

        when:
        Product intriguingProduct = products[0]
        Product productToUpdate = intriguingProduct.withName(newProductName)
        productClient.updateProduct(intriguingProduct.id(), productToUpdate).block()

        Product updatedProduct = productClient.getProduct(intriguingProduct.id()).block()

        then:
        updatedProduct.name() == newProductName

        when:
        List<Product> mProducts = productClient.getProducts().block()

        then:
        mProducts.size() == 6
    }
}
