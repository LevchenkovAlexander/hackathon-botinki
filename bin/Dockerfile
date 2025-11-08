
FROM openjdk:25-jdk

COPY target/demo-1.0.0.jar app.jar

WORKDIR /app

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "/app.jar"]
