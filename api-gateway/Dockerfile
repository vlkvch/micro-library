FROM gradle:8.10-jdk21-alpine AS build
WORKDIR /usr/src/api-gateway

COPY build.gradle.kts .
COPY src src

RUN --mount=type=cache,target=/root/.gradle gradle clean build


FROM gcr.io/distroless/java21:nonroot

COPY --from=build /usr/src/api-gateway/build/libs/api-gateway-0.3.0.jar /home/nonroot/api-gateway.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "api-gateway.jar"]
