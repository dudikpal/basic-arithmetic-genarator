#FROM zulu16jdk as builder
FROM adoptopenjdk as builder
WORKDIR application
COPY target/basic-arithmetic-genarator-0.0.1-SNAPSHOT.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract
#FROM zulu16jdk
FROM adoptopenjdk
WORKDIR application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]