FROM openjdk:18-slim-bullseye as jre-build
WORKDIR /home/app
COPY layers/libs /home/app/libs
COPY layers/resources /home/app/resources
COPY layers/application.jar /home/app/application.jar

# find JDK dependencies dynamically from jar
RUN jdeps \
  # dont worry about missing modules
    --ignore-missing-deps \
    # suppress any warnings printed to console
    -q \
    # java release version targeting
    --multi-release 17 \
    # output the dependencies at end of run
    --print-module-deps \
    # specify the the dependencies for the jar
    --class-path /home/app/libs/* \
    # pipe the result of running jdeps on the app jar to file
    /home/app/application.jar > jre-deps.info \
    && \
    jlink --verbose \
    --compress 2 \
    --strip-java-debug-attributes \
    --no-header-files \
    --no-man-pages \
    --output jre \
    --add-modules $(cat jre-deps.info)
#
FROM debian:bullseye-slim
#
ENV LANG='en_US.UTF-8' LANGUAGE='en_US:en' LC_ALL='en_US.UTF-8'
## https://github.com/Docker-Hub-frolvlad/docker-alpine-glibc
#
ENV JAVA_HOME /opt/jdk
ENV PATH $JAVA_HOME/bin:$PATH

### copy the custom JRE produced from jlink
COPY --from=jre-build /home/app/jre $JAVA_HOME

## copy the app dependencies
COPY --from=jre-build /home/app/libs/* /home/app/libs/

COPY --from=jre-build /home/app/resources/* /home/app/resources

## copy the app
COPY --from=jre-build /home/app/application.jar /home/app/application.jar
#
ENTRYPOINT ["java", "-jar", "/home/app/application.jar"]
