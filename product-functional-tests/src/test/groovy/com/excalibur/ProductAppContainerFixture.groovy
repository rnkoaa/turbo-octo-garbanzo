package com.excalibur;

trait ProductAppContainerFixture {

    Map<String, Object> getContainerConfiguration() {
        if (ProductAppContainer.productAppContainer == null || !ProductAppContainer.productAppContainer.isRunning()) {
            ProductAppContainer.init()
        }

//        HttpU
        int port = ProductAppContainer.productAppContainer.getMappedPort(8080)
        String containerName = ProductAppContainer.productAppContainer.host
        return [
                "application.server": containerName,
                "application.port"  : String.valueOf(port)
        ]

    }

}
