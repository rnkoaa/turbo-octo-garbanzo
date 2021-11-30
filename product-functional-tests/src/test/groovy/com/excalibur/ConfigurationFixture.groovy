package com.excalibur

trait ConfigurationFixture implements ProductAppContainerFixture {
    Map<String, Object> getConfiguration() {
        Map<String, Object> m = [:]
        if (specName) {
            m['spec.name'] = specName
        }
        m += containerConfiguration
        return m
    }

    String getSpecName() {
        null
    }
}
