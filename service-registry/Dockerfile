FROM gradle:8.10-jdk21-alpine AS build
WORKDIR /usr/src/service-registry

COPY build.gradle.kts .
COPY src src

RUN --mount=type=cache,target=/root/.gradle gradle clean build


FROM gcr.io/distroless/java21:nonroot

COPY --from=build /usr/src/service-registry/build/libs/service-registry-0.1.0.jar /home/nonroot/service-registry.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "service-registry.jar"]
