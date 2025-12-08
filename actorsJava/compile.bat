@echo off
setlocal enabledelayedexpansion

echo ==================================================
echo      Construction et deploiement de Actor.jar
echo ==================================================
echo.

:: -----------------------------
:: CONFIGURATION
:: -----------------------------
set "ACTOR_SRC=%~dp0src\com\actor"
set "OUTPUT_DIR=%~dp0classes"
set "JAR_NAME=Actor.jar"

:: Chemin absolu du projet (à adapter selon votre structure)
set "PROJECT_PATH=%~dp0\.."

:: -----------------------------
:: ETAPE 1 — Nettoyer la build precedente
:: -----------------------------
echo Nettoyage de l'ancienne build...
if exist "%OUTPUT_DIR%" rmdir /s /q "%OUTPUT_DIR%"
if exist "%~dp0%JAR_NAME%" del /f "%~dp0%JAR_NAME%"
mkdir "%OUTPUT_DIR%"
echo.

:: -----------------------------
:: ETAPE 2 — Compilation du code source
:: -----------------------------
echo Compilation du code source...
call javac -d "%OUTPUT_DIR%" "%ACTOR_SRC%\Actor.java"
if errorlevel 1 (
    echo Erreur lors de la compilation.
    pause
    exit /b 1
)
echo Compilation reussie.
echo.

:: -----------------------------
:: ETAPE 3 — Creation du fichier JAR
:: -----------------------------
echo Creation du fichier %JAR_NAME%...
call jar cf "%~dp0%JAR_NAME%" -C "%OUTPUT_DIR%" .
if errorlevel 1 (
    echo Erreur lors de la creation     du JAR.
    pause
    exit /b 1
)
echo JAR cree : %~dp0%JAR_NAME%
echo.

:: Copier Actor.jar dans chaque dossier libs des sous-dossiers de ProjetJEE/springBoot/
for /d %%d in ("%PROJECT_PATH%\springBoot\*") do (    
    if /I "%%~nd" NEQ "Starters" (
    echo ================================================================================================================
    echo               Dossier %%d
    echo ================================================================================================================
    echo.
    echo Copie de Actor.jar dans %%d\libs...
    mkdir "%%d\libs" 2>nul
    copy /Y Actor.jar "%%d\libs\"

    :: Exécuter mvn install:install-file
    echo Installation de Actor.jar via Maven dans %%d...
    cd /d "%%d"
    :: Exécuter Maven avec seulement BUILD SUCCESS/FAILURE et le temps
    call mvn install:install-file "-Dfile=%%d\libs\%JAR_NAME%" "-DgroupId=com.actor" "-DartifactId=actor" "-Dversion=1.0" "-Dpackaging=jar" 2>&1 | findstr /r /c:"BUILD SUCCESS" /c:"BUILD FAILURE" /c:"Total time:"
        if errorlevel 1 (
            echo ERREUR : BUILD FAILURE lors de l'installation Maven dans %%d.
            pause
            exit /b 1
        )
        echo Compilation Maven dans %%d...
        call mvn clean compile 2>&1 
        ::| findstr /r /c:"BUILD SUCCESS" /c:"BUILD FAILURE" /c:"Total time:"
        if errorlevel 1 (
            echo ERREUR : BUILD FAILURE lors de la compilation Maven dans %%d.
            pause
            exit /b 1
        )
        popd
))
echo Processus terminé.
pause