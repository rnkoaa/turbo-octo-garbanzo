package com.excalibur;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;

@MicronautTest
class ProductTest {

//    @Inject
//    private ProductClient productClient;
//
//    @Test
//    void productCanBeRetrieved() {
//        List<Product> products = productClient.getProducts().block();
//        assertThat(products).hasSize(6);
//    }
//
//    @AfterEach
//    void resetInventory() {
//         productClient.resetInventory().block();
//    }
//
//    @Test
//    void productCanBeCreatedAndUpdated() {
//        var product = new Product(UUID.randomUUID(), "My Product", new ProductVariant(UUID.randomUUID(), "Variant 1"));
//        var response = productClient.create(product).block();
//        assertThat(response).isNotNull();
//        assertThat(response.code()).isEqualTo(HttpStatus.CREATED.getCode());
//        assertThat(response.body()).isEqualTo(product);
//
//        var updateProduct = new Product(product.id(), "My Updated Product", product.variant());
//        var res = productClient.updateProduct(updateProduct.id(), updateProduct).block();
//
//        assertThat(res).isNotEqualTo(product).isEqualTo(updateProduct);
//
//    }
//
//    @Test
//    void productCanBeCreated() {
//        var product = new Product(UUID.randomUUID(), "My Product", new ProductVariant(UUID.randomUUID(), "Variant 1"));
//        var response = productClient.create(product).block();
//        assertThat(response).isNotNull();
//        assertThat(response.code()).isEqualTo(HttpStatus.CREATED.getCode());
//        assertThat(response.body()).isEqualTo(product);
//        assertThat(response.getHeaders().contains("location")).isTrue();
//
//        Map<String, List<String>> res = response.getHeaders().asMap();
//
//        assertThat(res.get("location")).hasSize(1);
//        assertThat(res.get("location").get(0)).contains(String.format("/product/%s", product.id()));
//    }
//
//    @Test
//    void retrieveSingleProduct() {
//        List<Product> products = productClient.getProducts().block();
//        assertThat(products).hasSize(6);
//        assertThat(products).isNotNull();
//        var product = products.get(0);
//        Product retrievedProduct = productClient.getProduct(product.id()).block();
//        assertThat(retrievedProduct).isNotNull();
//        assertThat(retrievedProduct).isEqualTo(product);
//    }

}
