#!/bin/sh

# https://linuxtut.com/how-to-make-spring-boot-docker-image-smaller-51713/

set -eu

readonly TARGET_JAR=$1
readonly TARGET_VER=$2

readonly TMP_DIR="tmp/product-app-jar"
mkdir -p ${TMP_DIR}

# trap 'rm -rf ${TMP_DIR}' EXIT

#Extract the jar
unzip -q "${TARGET_JAR}" -d "${TMP_DIR}"

jdeps \
    -classpath \'${TMP_DIR}/BOOT-INF/lib/*:${TMP_DIR}/BOOT-INF/classes:${TMP_DIR}\' \
    --print-module-deps \
    --ignore-missing-deps \
    --module-path ${TMP_DIR}/BOOT-INF/lib/javax.activation-api-1.2.0.jar \
    --recursive \
    --multi-release ${TARGET_VER} \
    -quiet \
    ${TMP_DIR}/org ${TMP_DIR}/BOOT-INF/classes ${TMP_DIR}/BOOT-INF/lib/*.jar


# FROM adoptopenjdk/openjdk11:alpine AS java-build
# WORKDIR /jlink
# ENV PATH $JAVA_HOME/bin:$PATH
# RUN jlink --strip-debug --no-header-files --no-man-pages --compress=2 --module-path $JAVA_HOME \
#     --add-modules java.base,java.desktop,java.instrument,java.management.rmi,java.naming,java.prefs,java.scripting,java.security.jgss,java.sql,jdk.httpserver,jdk.unsupported \
#     --output jre-min

