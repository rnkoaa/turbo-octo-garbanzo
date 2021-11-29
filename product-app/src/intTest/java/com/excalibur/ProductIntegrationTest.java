package com.excalibur;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.excalibur.product.Product;
import com.excalibur.product.ProductVariant;
import io.micronaut.http.HttpStatus;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

@MicronautTest
class ProductIntegrationTest {

    @Inject
    private ProductIntegrationClient productIntegrationClient;

    @Test
    void productCanBeRetrieved() {
        List<Product> products = productIntegrationClient.getProducts().block();
        assertThat(products).hasSize(6);
    }

    @AfterEach
    void resetInventory() {
        productIntegrationClient.resetInventory().block();
    }

    @Test
    void productCanBeCreatedAndUpdated() {
        var product = new Product(UUID.randomUUID(), "My Product", new ProductVariant(UUID.randomUUID(), "Variant 1"));
        var response = productIntegrationClient.create(product).block();
        assertThat(response).isNotNull();
        assertThat(response.code()).isEqualTo(HttpStatus.CREATED.getCode());
        assertThat(response.body()).isEqualTo(product);

        var updateProduct = new Product(product.id(), "My Updated Product", product.variant());
        var res = productIntegrationClient.updateProduct(updateProduct.id(), updateProduct).block();

        assertThat(res).isNotEqualTo(product).isEqualTo(updateProduct);

    }

    @Test
    void productCanBeCreated() {
        var product = new Product(UUID.randomUUID(), "My Product", new ProductVariant(UUID.randomUUID(), "Variant 1"));
        var response = productIntegrationClient.create(product).block();
        assertThat(response).isNotNull();
        assertThat(response.code()).isEqualTo(HttpStatus.CREATED.getCode());
        assertThat(response.body()).isEqualTo(product);
        assertThat(response.getHeaders().contains("location")).isTrue();

        Map<String, List<String>> res = response.getHeaders().asMap();

        assertThat(res.get("location")).hasSize(1);
        assertThat(res.get("location").get(0)).contains(String.format("/product/%s", product.id()));
    }

    @Test
    void retrieveSingleProduct() {
        List<Product> products = productIntegrationClient.getProducts().block();
        assertThat(products).hasSize(6);
        assertThat(products).isNotNull();
        var product = products.get(0);
        Product retrievedProduct = productIntegrationClient.getProduct(product.id()).block();
        assertThat(retrievedProduct).isNotNull();
        assertThat(retrievedProduct).isEqualTo(product);
    }

}
