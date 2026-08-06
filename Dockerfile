# 1. Étape de compilation (Build)
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copie le dossier backend
COPY backend/ /app/

# Compilation depuis le dossier contenant pom.xml
RUN mvn clean package -DskipTests

# 2. Étape d'exécution (Runtime)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]