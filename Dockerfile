FROM openjdk:17-oracle
WORKDIR /app
COPY target/gym-app-0.0.1-SNAPSHOT.jar app.jar
COPY src/main/resources/application.properties /app/dev/app/application.properties
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]