#!/bin/sh

# https://medium.com/azulsystems/using-jlink-to-build-java-runtimes-for-non-modular-applications-9568c5e70ef4

IMAGE_NAME=mini-java

# --strip-java-debug-attributes
jlink --verbose --compress 2 --strip-debug --no-header-files --no-man-pages --output jre --add-modules java.datatransfer,java.desktop,java.logging,java.management,java.naming,java.rmi,java.scripting,java.sql,java.transaction.xa,java.xml,jdk.jsobject,jdk.unsupported,jdk.unsupported.desktop,jdk.xml.dom 

# $JAVA_HOME/bin/jlink --add-modules java.sql,java.rmi,\
# 	jdk.management.agent,java.transaction.xa,java.logging,\
# 	java.xml,java.management,jdk.unsupported,java.datatransfer,\
# 	jdk.internal.jvmstat,java.instrument,java.security.jgss,\
# 	jdk.management,java.naming,java.desktop,java.compiler,\
# 	java.prefs,java.security.sasl,jdk.jconsole,\
# 	java.management.rmi,jdk.attach,java.base \
# 	--output $IMAGE_NAME --strip-debug --no-header-files \
# 	--no-man-pages --compress 2
