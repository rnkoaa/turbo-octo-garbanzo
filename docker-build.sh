#!/bin/sh

set eux

./gradlew buildLayers

arch=$(uname -m)

cp src/main/docker/"$arch".Dockerfile build/docker/Dockerfile

docker buildx build build/docker -t rnkoaa/product-app:0.1