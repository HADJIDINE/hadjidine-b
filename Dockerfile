# 1. Image de build Maven avec Java 21
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Limite la mémoire utilisée par Maven pour éviter de saturer Render
ENV MAVEN_OPTS="-Xms256m -Xmx512m"

COPY . .

# Compilation du projet Spring Boot
RUN mvn clean package -DskipTests

# 2. Image d'exécution légère
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]