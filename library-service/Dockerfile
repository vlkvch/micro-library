FROM gradle:8.10-jdk21-alpine AS build
WORKDIR /usr/src/library-service

COPY build.gradle.kts .
COPY src src

RUN --mount=type=cache,target=/root/.gradle gradle clean build


FROM gcr.io/distroless/java21:nonroot

COPY --from=build /usr/src/library-service/build/libs/library-service-0.1.0.jar /home/nonroot/library-service.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/home/nonroot/library-service.jar"]
