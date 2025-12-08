@echo off
setlocal enabledelayedexpansion

echo ==================================================
echo      Build ActorLibrary with Maven
echo ==================================================
echo.

:: -----------------------------
:: CONFIGURATION
:: -----------------------------
set "LIB_PROJECT=%~dp0\ActorLibrary"
set "JAR_OUTPUT=%LIB_PROJECT%\target"
set "PROJECT_PATH=%~dp0\.."
set "JAR_NAME=actor-1.0.jar"

echo Projet library Maven : %LIB_PROJECT%
echo.

:: -----------------------------
:: ETAPE 1 — Build Maven du projet ActorLibrary
:: -----------------------------
echo Construction de ActorLibrary...
cd /d "%LIB_PROJECT%"
call mvn clean install -q -DskipTests
if errorlevel 1 (
    echo ERREUR : La compilation Maven a echoue.
    pause
    exit /b 1
)
echo Build Maven reussi.
echo.

:: Trouver le JAR genere automatiquement
for %%f in ("%JAR_OUTPUT%\*.jar") do (
    set "BUILT_JAR=%%f"
)

echo JAR detecte : %BUILT_JAR%
echo.

:: -----------------------------
:: ETAPE 2 — Copie du JAR dans tous les sous-projets SpringBoot
:: -----------------------------
echo Copie du JAR dans les sous-projets...

for /d %%d in ("%PROJECT_PATH%\springBoot\*") do (
    if /I "%%~nd" NEQ "Starters" (
        if /I "%%~nd" NEQ "" (      
        echo ====================================================================
        echo   Projet : %%d
        echo ====================================================================

        mkdir "%%d\libs" 2>nul
        copy /Y "%BUILT_JAR%" "%%d\libs\%JAR_NAME%"
        
        echo Compilation Maven dans %%d ...
        cd /d "%%d"
        call mvn clean compile -q

        if errorlevel 1 (
            echo ERREUR Maven dans %%d !
            pause
            exit /b 1
        )
        echo OK.
        echo.
    )
    )
)

echo ==================================================
echo       Processus terminé avec succès !
echo ==================================================
pause
