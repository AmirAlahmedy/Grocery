FROM maven:3.9.5-eclipse-temurin-21 AS build
WORKDIR /workspace/app

COPY pom.xml ./
COPY inventory-app/pom.xml ./inventory-app/
COPY inventory-test/pom.xml ./inventory-test/
COPY inventory-bdd/pom.xml ./inventory-bdd/
RUN mvn dependency:go-offline -pl inventory-app -am
COPY inventory-app/src ./inventory-app/src
RUN mvn clean package -pl inventory-app -DskipTests

FROM eclipse-temurin:21-jre
RUN addgroup --system app && adduser --system --ingroup app app || true
WORKDIR /app

COPY --from=build /workspace/app/inventory-app/target/*-SNAPSHOT.jar ./app.jar
USER app
EXPOSE 8081

ENV JAVA_TOOL_OPTIONS="-XX:+UseContainerSupport -Xms128m -Xmx512m"
ENTRYPOINT ["sh","-c","exec java $JAVA_TOOL_OPTIONS -jar /app/app.jar"]
