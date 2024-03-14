FROM gradle:8.4.0-jdk17 AS build

WORKDIR /db-crud

COPY build.gradle .
COPY settings.gradle .
COPY src /db-crud/src

RUN gradle clean build -x test

FROM eclipse-temurin:17-jdk-alpine

COPY --from=build /db-crud/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]