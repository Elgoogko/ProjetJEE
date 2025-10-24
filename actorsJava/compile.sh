#!/bin/bash
echo "Building Actor library..."

# Nettoyer la build précédente
rm -rf classes
rm -f Actor.jar

# Créer le dossier de sortie
mkdir -p classes

# Créer le dossier de package pour les sources compilées
mkdir -p src/com/actor

# Déplacer Actor.java dans le package
# (Si ce n'est pas déjà fait, il faut modifier la déclaration package dans Actor.java)
# Ajoute en haut de Actor.java :
# package com.actor;

# Compiler les sources avec le package
javac -d classes src/com/actor/*.java

# Créer le JAR avec la structure de package correcte
jar cf Actor.jar -C classes .

echo "Build completed: Actor.jar"