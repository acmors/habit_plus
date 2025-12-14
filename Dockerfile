FROM ubuntu:latest AS build

RUN apt-get update && \
    apt-get install -y openjdk-21-jdk maven git && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

EXPOSE 8080

COPY --from=build /app/target/HabitPlus-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]