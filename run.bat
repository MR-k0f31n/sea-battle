     @echo off
     setlocal

     REM Находим путь к текущему исполняемому файлу
     for %%I in ("%~dp0.") do set "SCRIPT_DIR=%%~fI"

     REM Путь к jar файлу относительно скрипта
     set "JAR_PATH=%SCRIPT_DIR%\sea_battle-1.0-SNAPSHOT-jar-with-dependencies.jar"

     REM Проверяем существование jar файла
     if not exist "%JAR_PATH%" (
         echo Ошибка: Jar файл не найден в %JAR_PATH%
         exit /b 1
     )

     REM Запускаем jar файл
     java -jar "%JAR_PATH%"
