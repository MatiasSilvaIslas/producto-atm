FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY atm-microservice/target/atm-microservice-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
