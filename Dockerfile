# 1. Image de build Maven avec Java 21
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

COPY . .

# Compilation directement dans le dossier du projet
RUN mvn -f portfolio-family-chat/pom.xml clean package -DskipTests -Dfile.encoding=UTF-8

# 2. Image d'exécution
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=build /app/portfolio-family-chat/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]