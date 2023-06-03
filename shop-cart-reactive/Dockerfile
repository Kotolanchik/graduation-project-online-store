FROM openjdk:20

WORKDIR /app

# Копируем JAR файл в рабочую директорию контейнера
COPY target/shop-cart-reactive-0.0.1-SNAPSHOT.jar .

# Опционально: копируем внешние файлы, необходимые для приложения
# COPY external-files/ /app/external-files/

# Опционально: задаем переменные окружения, если требуется
# ENV ENV_VARIABLE_NAME=value

# Опционально: открываем порты, если приложению нужно обслуживать запросы
EXPOSE 8081

# Запускаем приложение
CMD ["java", "-jar", "shop-cart-reactive-0.0.1-SNAPSHOT.jar"]