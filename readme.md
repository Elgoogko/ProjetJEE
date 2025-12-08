# Projet Spring Boot Microservices avec Eureka

**Description** : Ce projet est une application basée sur une architecture microservices utilisant Spring Boot, Eureka pour la découverte de services, et d'autres technologies modernes. Il est organisé en deux dossiers principaux : `desc` (pour la documentation et les ressources pédagogiques) et `springBoot` (pour les microservices).

---

## 📌 Table des matières
- [Prérequis](#prérequis)
- [Structure du projet](#structure-du-projet)
- [Technologies utilisées](#technologies-utilisées)
- [Installation et exécution](#installation-et-exécution)
- [Documentation](#documentation)
- [Contribuer](#contribuer)
- [Licence](#licence)

---

## 🔧 Prérequis
- **Java 17** : Version requise pour exécuter les microservices.
- **Maven** : Outil de gestion de dépendances et de build.
- **IDE** : IntelliJ IDEA, Eclipse, ou tout autre IDE compatible avec Spring Boot.

---

## 📂 Structure du projet

<custom-element data-json="%7B%22type%22%3A%22table-metadata%22%2C%22attributes%22%3A%7B%22title%22%3A%22Structure%20des%20dossiers%22%7D%7D" />

| Dossier | Description |
|---------|-------------|
| `desc/` | Contient les ressources pédagogiques : diapositives, diagrammes UML, documentation technique, etc. Ces fichiers **ne sont pas utilisés directement par le code**. |
| `springBoot/` | Contient les microservices Spring Boot. Chaque microservice est un projet Maven indépendant. |
| `springBoot/Starters/` | Contient les méthodes pour démmarer plus facilement le projet en entier |

---

## 🛠 Technologies utilisées

<custom-element data-json="%7B%22type%22%3A%22table-metadata%22%2C%22attributes%22%3A%7B%22title%22%3A%22Technologies%22%7D%7D" />

| Technologie | Version | Description |
|-------------|---------|-------------|
| **Spring Boot** | 3.x | Framework principal pour les microservices. |
| **Eureka Server** | - | Service de découverte pour la gestion des microservices. |
| **Eureka Client** | - | Client pour s'enregistrer et découvrir d'autres services. |
| **H2 Database** | - | Base de données en mémoire pour le développement et les tests. |
| **Thymeleaf** | - | Moteur de template pour les interfaces web. |
| **Spring Web** | - | Module pour créer des applications web RESTful. |
| **Maven** | 3.x | Outil de gestion de dépendances et de build. |

---

## 🚀 Installation et exécution

### 1. Cloner le dépôt
```bash
git clone [URL_DU_DEPOT]
cd [NOM_DU_PROJET]
```

### 2. Démarrer le projet
Naviger dans le dossier `springBoot/Starters/`

#### Pour Windows
Démmarer le projet
```batch
./start.bat
```
Arrêter le projet 
```batch
./stop.bat
```

#### Pour Linux

Mettre en executable les fichier start.sh et stop.sh
```bash
chmod +x start.sh
chmod +x stop.sh
```

Démarer le projet
```bash
./start.sh
```

Arrêter le projet 
```bash
./stop.sh
```

**Attention : utilisé le stop.bat / stop.sh va arrêter tous les processus Java de la machine, qu'ils fassent partie du projet ou non**

## Installer le Actor.jar
Dans le pom.xml, il faut mettre : 
```xml
	<dependency>
		<groupId>com.actor</groupId>
		<artifactId>actor-interface</artifactId>
		<version>1.0</version>
	</dependency>
```

Ensuite, il faut exécuter les commandes suivantes dans le dossier actorsManager
Sur Linux : 
```bash
./compileLibrary.sh
```

Sur Windows : 
```batch
compileLibrary.bat
```

Sinon, pour éxécuter compile.bat / compile.sh partout, il suffit d'utiliser Docker (si disponible sur le système)
```batch
docker build -t actor-build .
docker run --rm -v %cd%:/app actor-build
```
