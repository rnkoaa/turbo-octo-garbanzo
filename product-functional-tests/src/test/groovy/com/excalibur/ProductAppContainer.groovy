package com.excalibur

import org.testcontainers.containers.GenericContainer
import org.testcontainers.utility.DockerImageName;

class ProductAppContainer {
//    new GenericContainer(DockerImageName.parse("jboss/wildfly:9.0.1.Final"))
    static GenericContainer productAppContainer
    static final DockerImageName PRODUCT_APP_IMAGE = DockerImageName.parse("rnkoaa/product-app:0.1");

    static init() {
        if (productAppContainer == null) {
            productAppContainer = new GenericContainer(PRODUCT_APP_IMAGE)
                    .withNetworkAliases("product-app")
                    .withExposedPorts(8080)
            productAppContainer.start()
        }
    }
}
