#!/bin/bash

echo "Arrêt de tous les services Spring Boot..."

# Arrêter les processus mvn spring-boot:run
pkill -f "mvn spring-boot:run"
# tue tous les executions spring boot donc faire attention de ne pas lancer d'autres serveur a coté
# a corriger pour eliminer les bon si possible.

# Arrêter les processus Java Spring
pkill -f "java.*spring"

# Nettoyer le fichier PID s'il existe
PID_FILE="../java_pids.txt"
[ -f "$PID_FILE" ] && rm -f "$PID_FILE"

echo "Tous les microservices ont été arrêtés."