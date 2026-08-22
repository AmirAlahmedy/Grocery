FROM maven:3.9.5-eclipse-temurin-21 AS build
WORKDIR /workspace/app

COPY pom.xml ./
COPY inventory-app/src ./src
RUN mvn -B -DskipTests package

FROM eclipse-temurin:21-jre
RUN addgroup --system app && adduser --system --ingroup app app || true
WORKDIR /app

COPY --from=build /workspace/app/target/*-SNAPSHOT.jar ./app.jar
USER app
EXPOSE 8081

ENV JAVA_TOOL_OPTIONS="-XX:+UseContainerSupport -Xms128m -Xmx512m"
ENTRYPOINT ["sh","-c","exec java $JAVA_TOOL_OPTIONS -jar /app/app.jar"]
