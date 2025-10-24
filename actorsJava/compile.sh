#!/bin/bash
echo "Building library..."

# Nettoyer la build précédente
rm -rf classes
rm -f Actor.jar

# Créer le dossier de sortie
mkdir -p classes

# Compiler
javac -d classes *.java

# Créer le JAR
jar cf Actor.jar -C classes .

echo "Build completed: Actor.jar"