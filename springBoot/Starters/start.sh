#!/bin/bash

# Créer un fichier pour stocker les PIDs des processus Java
PID_FILE="java_pids.txt"
rm -f "$PID_FILE"

cd ".."

# Lister tous les dossiers dans le répertoire courant (sauf start.sh)
for dir in */; do
    if [ "$dir" != "./" ] && [ "$dir" != "../" ] && [ "$dir" != "Starters" ]; then
        (cd "$dir" && mvn spring-boot:run > /dev/null 2>&1 & echo $! >> "../$PID_FILE" &)
        if [ "$dir" == "main" ]; then
            echo "Démmarage du serveur"
            sleep 5
        else
            echo "Démarrage du microservice : $dir"
        fi
    fi
done

# Attendre quelques secondes pour laisser le temps aux services de démarrer
sleep 10

# Ouvrir l'URL dans le navigateur par défaut
xdg-open "http://localhost:8761/" || open "http://localhost:8761/"

echo "Pour arrêter tous les microservices, exécutez : ./stop.sh"
