#!/bin/sh

set eux

./gradlew buildLayers

cp src/main/docker/Dockerfile build/docker

docker buildx build build/docker -t rnkoaa/product-app:0.1