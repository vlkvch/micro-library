FROM gradle:8.10-jdk21-alpine AS build
WORKDIR /usr/src/book-service

COPY build.gradle.kts .
COPY src src

RUN --mount=type=cache,target=/root/.gradle gradle clean build


FROM gcr.io/distroless/java21:nonroot

COPY --from=build /usr/src/book-service/build/libs/book-service-0.3.0.jar /home/nonroot/book-service.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "book-service.jar"]
