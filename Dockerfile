# 1. Image officielle Maven avec Java 21 pour tout compiler
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copie tous les fichiers du dossier courant vers le conteneur
COPY . .

# Compilation Maven en ignorant les tests pour aller plus vite
RUN mvn clean package -DskipTests

# 2. Image légère Java 21 pour l'exécution
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copie le fichier .jar généré
COPY --from=build /app/target/*.jar app.jar

# Port d'écoute par défaut
EXPOSE 8080

# Commande de démarrage
ENTRYPOINT ["java", "-jar", "app.jar"]