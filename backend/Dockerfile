FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src/main ./src/main
RUN mvn clean package -DskipTests

FROM openjdk:24-ea-17-jdk-slim-bullseye
WORKDIR /app
EXPOSE 8080
COPY --from=build /app/target/grocery-*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]