@echo off
setlocal enabledelayedexpansion

:: Tuer tous les processus Java (microservices Spring Boot)
taskkill /F /IM java.exe

echo Tous les microservices ont été arrêtés.
