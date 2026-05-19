# Étape 1 : Construction de l'application (Build)
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copie des fichiers de configuration Maven pour mettre en cache les dépendances
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

# Donner les permissions d'exécution au wrapper Maven
RUN chmod +x ./mvnw

# Télécharger les dépendances (évite de tout retélécharger si le code change)
RUN ./mvnw dependency:go-offline -B

# Copier le code source et compiler
COPY src src
RUN ./mvnw clean package -DskipTests

# Étape 2 : Image finale d'exécution (Run) - Plus légère
FROM eclipse-temurin:21-jre
WORKDIR /app

# Création d'un utilisateur non-root pour plus de sécurité sur Render
RUN useradd -m appuser
USER appuser

# Copier le JAR généré à l'étape précédente
COPY --from=build /app/target/*.jar app.jar

# Configuration des variables d'environnement pour Render
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]