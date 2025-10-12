#!/bin/bash

# Lire les PIDs depuis le fichier
PID_FILE="java_pids.txt"

if [ -f "$PID_FILE" ]; then
    while read -r pid; do
        kill -9 "$pid" 2>/dev/null
    done < "$PID_FILE"
    rm -f "$PID_FILE"
    echo "Tous les microservices ont été arrêtés."
else
    echo "Aucun microservice en cours d'exécution."
fi
