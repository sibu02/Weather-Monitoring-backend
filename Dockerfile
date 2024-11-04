FROM maven:3.8.6-openjdk-8 as build
WORKDIR /app
COPY pom.xml ./
RUN mvn dependency:go-offline
COPY /src ./src
RUN mvn clean package -DskipTests

FROM openjdk:8-jre
WORKDIR /app
COPY --from=build app/target/weather_monitoring_app.jar ./
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/weather_monitoring_app.jar"]
C:/project/zeotopInternsipAssignments/weather-monitoring