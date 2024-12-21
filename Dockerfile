FROM gradle:8-jdk21 AS builder

WORKDIR /src

COPY . .

RUN gradle build --no-daemon

FROM openjdk:21-jdk-slim-bullseye

EXPOSE 8080

WORKDIR /src

COPY --from=builder /src/build/libs/ecommerce-0.0.1-SNAPSHOT.jar /src/app.jar

CMD ["java", "-jar", "app.jar"]
