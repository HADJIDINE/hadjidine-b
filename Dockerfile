# 1. Image de build Maven
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

COPY . .

# Détection automatique du fichier pom.xml et compilation
RUN POM_PATH=$(find . -name "pom.xml" -not -path "*/target/*" | head -n 1) && \
    POM_DIR=$(dirname "$POM_PATH") && \
    echo "Trouvé pom.xml dans: $POM_DIR" && \
    mvn -f "$POM_DIR/pom.xml" clean package -DskipTests

# 2. Image d'exécution
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Récupération du .jar généré
COPY --from=build /app/**/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]