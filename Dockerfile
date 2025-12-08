# Dockerfile
FROM maven:3.8.6-openjdk-11 AS builder

WORKDIR /app

# Copie du projet
COPY . .

# Build du JAR
RUN mkdir -p classes && \
    javac -d classes src/com/actor/Actor.java && \
    jar cf Actor.jar -C classes .

# Déploiement dans les sous-dossiers de springBoot
RUN for dir in /app/springBoot/*; do \
        if [ "$(basename "$dir")" != "Starters" ]; then \
            mkdir -p "$dir/libs" && \
            cp Actor.jar "$dir/libs/" && \
            cd "$dir" && \
            mvn install:install-file -Dfile=libs/Actor.jar -DgroupId=com.actor -DartifactId=actor -Dversion=1.0 -Dpackaging=jar && \
            mvn clean compile; \
        fi; \
    done
