#!/bin/bash

echo "=================================================="
echo "      Build ActorLibrary with Maven"
echo "=================================================="
echo ""

# -----------------------------
# CONFIGURATION
# -----------------------------
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
LIB_PROJECT="$SCRIPT_DIR/ActorLibrary"
JAR_OUTPUT="$LIB_PROJECT/target"
PROJECT_PATH="$SCRIPT_DIR/.."
JAR_NAME="actor-1.0.jar"

echo "Projet library Maven : $LIB_PROJECT"
echo ""

# -----------------------------
# ETAPE 1 — Build Maven du projet ActorLibrary
# -----------------------------
echo "Construction de ActorLibrary..."
cd "$LIB_PROJECT" || exit 1
mvn clean install -q -DskipTests
if [ $? -ne 0 ]; then
    echo "ERREUR : La compilation Maven a echoue."
    read -p "Appuyez sur une touche pour continuer..." -n1 -s
    exit 1
fi
echo "Build Maven reussi."
echo ""

# Trouver le JAR genere automatiquement
BUILT_JAR=$(find "$JAR_OUTPUT" -name "*.jar" -type f | head -n 1)

echo "JAR detecte : $BUILT_JAR"
echo ""

# -----------------------------
# ETAPE 2 — Copie du JAR dans tous les sous-projets SpringBoot
# -----------------------------
echo "Copie du JAR dans les sous-projets..."

for dir in "$PROJECT_PATH/springBoot"/*/; do
    dirname=$(basename "$dir")
    
    if [[ "${dirname,,}" != "starters" && -n "$dirname" ]]; then
        echo "===================================================================="
        echo "   Projet : $dir"
        echo "===================================================================="

        mkdir -p "$dir/libs"
        cp -f "$BUILT_JAR" "$dir/libs/$JAR_NAME"
        
        echo "Compilation Maven dans $dir ..."
        cd "$dir" || continue
        mvn clean compile -q

        if [ $? -ne 0 ]; then
            echo "ERREUR Maven dans $dir !"
            read -p "Appuyez sur une touche pour continuer..." -n1 -s
            exit 1
        fi
        echo "OK."
        echo ""
    fi
done

echo "=================================================="
echo "       Processus terminé avec succès !"
echo "=================================================="
read -p "Appuyez sur une touche pour continuer..." -n1 -s