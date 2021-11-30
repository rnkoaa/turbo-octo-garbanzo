package com.excalibur

trait LeakageDetector {

    boolean hasLeakage() {
//        if(client.count)
        return false;
    }

}

/*
trait LeakageDetector extends RepositoriesFixture {
 boolean hasLeakage() {
 if (sensorRepository.count() > 0) {
 println "there are still sensors"
 }
 ...
 sensorRepository.count() > 0 | ...
 }
}
trait RepositoriesFixture {
 SensorRepository getSensorRepository() {
 applicationContext.getBean(SensorRepository)
 }
}

 */