#!/bin/bash

# Перейти в папку проекта
cd "$(dirname "$0")"

# Компиляция проекта
mvn clean install

# Запуск проекта
java -cp target/sea-battle-1.0-SNAPSHOT.jar App
