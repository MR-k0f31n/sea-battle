@echo off
REM Перейти в папку проекта
cd /d %~dp0

REM Компиляция проекта
mvn clean install

REM Запуск проекта
java -cp target\sea-battle-1.0-SNAPSHOT.jar App

pause