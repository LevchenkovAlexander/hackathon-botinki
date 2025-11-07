# Используем официальный образ с Java 25 от Eclipse Temurin
FROM eclipse-temurin:25-jdk

# Копируем jar-файл в контейнер
COPY target/demo-1.0.0.jar app.jar

# Устанавливаем рабочую директорию (не обязательно, но рекомендуется)
WORKDIR /app

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "/app.jar"]
