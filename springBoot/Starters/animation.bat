@echo off
echo Le programme est en cours d'exécution. Appuyez sur Entrée pour arrêter l'animation.

:loop
      for %%s in (\ / - "^|") do (
        :: Affiche le symbole
        <nul set /p "=%%~s"
        timeout /t 1 >nul
        :: Vérifie si une touche a été pressée
        if not "!lastkey!"=="" (
            goto :end
        )
        :: Retour au début de la ligne et efface le symbole
        <nul set /p "=\b \b"
    )
    :: Vérifie si une touche a été pressée (sans bloquer)
    set "lastkey="
    for /f "delims=" %%a in ('xcopy /w "%~f0" "%~f0" 2^>nul') do (
        if not "%%a"=="" set "lastkey=%%a"
    )
goto loop

:end
echo.
echo Animation terminée.
pause >nul
