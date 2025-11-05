::Version windows
@echo off
setlocal enabledelayedexpansion

:: Créer un fichier pour stocker les PIDs des processus Java
set PID_FILE=java_pids.txt
if exist "%PID_FILE%" del "%PID_FILE%"

cd ..

:: Lister tous les dossiers dans le répertoire courant (sauf start.bat)
for /d %%d in (*) do (
    if not "%%~nxd"=="%~nx0" (
        cd "%%d"
        :: Lancer mvn en arrière-plan et enregistrer le PID
        start "Microservice %%d" /B cmd /c "mvn spring-boot:run > NUL 2>&1"
        cd ..
        if "%%d"=="main" (
            echo Démarrage du serveur
            timeout /t 5 > nul
        ) else (
            echo Démarrage du microservice : %%d
        )
    )
)

:: Attendre quelques secondes pour laisser le temps aux services de démarrer
timeout /t 5 >nul

:: Ouvrir l'URL dans le navigateur par défaut
start "" "http://localhost:8761/"

:: Afficher un message pour arrêter les services
echo Pour arrêter tous les microservices, faite ENTREZ
pause
./stop.bat