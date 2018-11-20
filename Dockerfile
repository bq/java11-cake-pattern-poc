# First stage: Runs JLink to create the custom JRE
FROM hirokimatsumoto/alpine-openjdk-11 AS builder

WORKDIR /app

COPY target/*fat.jar app.jar

RUN jlink --module-path app.jar:$JAVA_HOME/jmods \
        --add-modules java.logging,java.base,java.sql,java.naming \
        --output dist \
        --compress 2 \
        --strip-debug \
        --no-header-files \
        --no-man-pages


# Second stage: setup your service over your custom JRE
FROM alpine:3.6

COPY src/main/resources/server-keystore.jks server-keystore.jks

WORKDIR /app
EXPOSE 8080

ENV JAVA_HOME=/app
ENV PATH="$PATH:$JAVA_HOME/bin"

COPY --from=builder /app/dist/ ./
COPY target/*fat.jar app.jar
COPY src/main/resources/config.json config.json
#COPY src/main/resources/keyfile.json keyfile.json

ENTRYPOINT [ "sh", "-c", "java -jar /app/app.jar" ]
