#!/bin/bash

# Находим путь к текущему скрипту
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

# Путь к jar файлу относительно скрипта
JAR_PATH="$SCRIPT_DIR/sea_battle-1.0-SNAPSHOT-jar-with-dependencies.jar"

# Проверяем существование jar файла
if [ ! -f "$JAR_PATH" ]; then
    echo "Ошибка: Jar файл не найден в $JAR_PATH"
    exit 1
fi

# Запускаем jar файл
java -jar "$JAR_PATH"
