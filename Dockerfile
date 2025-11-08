
FROM eclipse-temurin:21-jdk

COPY target/demo-1.0.0.jar app.jar

WORKDIR /app

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "/app.jar"]
