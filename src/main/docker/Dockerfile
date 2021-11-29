FROM openjdk:18-jdk-alpine3.14
#FROM openjdk:18-ea-24-jdk
#FROM openjdk:18-slim AS jre-build
WORKDIR /home/app
COPY layers/libs /home/app/libs
COPY layers/resources /home/app/resources
COPY layers/application.jar /home/app/application.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/home/app/application.jar"]
#
## find JDK dependencies dynamically from jar
#RUN jdeps \
#  # dont worry about missing modules
#    --ignore-missing-deps \
#    # suppress any warnings printed to console
#    -q \
#    # java release version targeting
#    --multi-release 17 \
#    # output the dependencies at end of run
#    --print-module-deps \
#    # specify the the dependencies for the jar
#    --class-path /home/app/libs/* \
#    # pipe the result of running jdeps on the app jar to file
#    /home/app/application.jar > jre-deps.info
#
### temp print jdeps output
##ENTRYPOINT cat /home/app/jre-deps.info
#
#RUN jlink --verbose \
#    --compress 2 \
#    --strip-java-debug-attributes \
#    --no-header-files \
#    --no-man-pages \
#    --output jre \
#    --add-modules $(cat jre-deps.info)
#
#FROM alpine:3.14
#
#ENV LANG='en_US.UTF-8' LANGUAGE='en_US:en' LC_ALL='en_US.UTF-8'
## https://github.com/Docker-Hub-frolvlad/docker-alpine-glibc
#
#ENV JAVA_HOME /opt/jdk
#ENV PATH $JAVA_HOME/bin:$PATH
#
##RUN apk add --no-cache \
##	tzdata musl-locales musl-locales-lang \
##    && rm -rf /var/cache/apk/*
#
#RUN apk add --no-cache tzdata --virtual .build-deps curl binutils zstd \
#    && GLIBC_VER="2.33-r0" \
#    && ALPINE_GLIBC_REPO="https://github.com/sgerrand/alpine-pkg-glibc/releases/download" \
#    && GCC_LIBS_URL="https://archive.archlinux.org/packages/g/gcc-libs/gcc-libs-10.1.0-2-x86_64.pkg.tar.zst" \
#    && GCC_LIBS_SHA256="f80320a03ff73e82271064e4f684cd58d7dbdb07aa06a2c4eea8e0f3c507c45c" \
#    && ZLIB_URL="https://archive.archlinux.org/packages/z/zlib/zlib-1%3A1.2.11-3-x86_64.pkg.tar.xz" \
#    && ZLIB_SHA256=17aede0b9f8baa789c5aa3f358fbf8c68a5f1228c5e6cba1a5dd34102ef4d4e5 \
#    && curl -LfsS https://alpine-pkgs.sgerrand.com/sgerrand.rsa.pub -o /etc/apk/keys/sgerrand.rsa.pub \
#    && SGERRAND_RSA_SHA256="823b54589c93b02497f1ba4dc622eaef9c813e6b0f0ebbb2f771e32adf9f4ef2" \
#    && echo "${SGERRAND_RSA_SHA256} */etc/apk/keys/sgerrand.rsa.pub" | sha256sum -c - \
#    && curl -LfsS ${ALPINE_GLIBC_REPO}/${GLIBC_VER}/glibc-${GLIBC_VER}.apk > /tmp/glibc-${GLIBC_VER}.apk \
#    && apk add --no-cache /tmp/glibc-${GLIBC_VER}.apk \
#    && curl -LfsS ${ALPINE_GLIBC_REPO}/${GLIBC_VER}/glibc-bin-${GLIBC_VER}.apk > /tmp/glibc-bin-${GLIBC_VER}.apk \
#    && apk add --no-cache /tmp/glibc-bin-${GLIBC_VER}.apk \
#    && curl -Ls ${ALPINE_GLIBC_REPO}/${GLIBC_VER}/glibc-i18n-${GLIBC_VER}.apk > /tmp/glibc-i18n-${GLIBC_VER}.apk \
#    && apk add --no-cache /tmp/glibc-i18n-${GLIBC_VER}.apk \
#    && /usr/glibc-compat/bin/localedef --force --inputfile POSIX --charmap UTF-8 "$LANG" || true \
#    && echo "export LANG=$LANG" > /etc/profile.d/locale.sh \
#    && curl -LfsS ${GCC_LIBS_URL} -o /tmp/gcc-libs.tar.zst \
#    && echo "${GCC_LIBS_SHA256} */tmp/gcc-libs.tar.zst" | sha256sum -c - \
#    && mkdir /tmp/gcc \
#    && zstd -d /tmp/gcc-libs.tar.zst --output-dir-flat /tmp \
#    && tar -xf /tmp/gcc-libs.tar -C /tmp/gcc \
#    && mv /tmp/gcc/usr/lib/libgcc* /tmp/gcc/usr/lib/libstdc++* /usr/glibc-compat/lib \
#    && strip /usr/glibc-compat/lib/libgcc_s.so.* /usr/glibc-compat/lib/libstdc++.so* \
#    && curl -LfsS ${ZLIB_URL} -o /tmp/libz.tar.xz \
#    && echo "${ZLIB_SHA256} */tmp/libz.tar.xz" | sha256sum -c - \
#    && mkdir /tmp/libz \
#    && tar -xf /tmp/libz.tar.xz -C /tmp/libz \
#    && mv /tmp/libz/usr/lib/libz.so* /usr/glibc-compat/lib \
#    && apk del --purge .build-deps glibc-i18n \
#    && rm -rf /tmp/*.apk /tmp/gcc /tmp/gcc-libs.tar* /tmp/libz /tmp/libz.tar.xz /var/cache/apk/*
#
#WORKDIR /home/app
#
### copy the custom JRE produced from jlink
#COPY --from=jre-build /home/app/jre $JAVA_HOME
#
#ENTRYPOINT ["/bin/sh"]