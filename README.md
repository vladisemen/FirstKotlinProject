![example workflow](https://github.com/vladisemen/FirstKotlinProject/actions/workflows/main.yml/badge.svg)

# Задание на Kotlin 

##  Ссылка на сайт
- [Сайт](https://vladisemen.github.io/FirstKotlinProject/)

##  Ссылки на планы заданий
- [1 план](Roadmap1.md)
- [2 план](Roadmap2.md)
- [3 план](Roadmap3.md)

## Cборка проекта 
1. В репозитории откройте терминал windows или PowerShell
2. В командной строке напишите и вставте текст: kotlinc src/* -cp lib/kotlinx-cli-0.2.1.jar -include-runtime -d main.jar

или

- Запустите скрипт BUILD.sh

##  Запуск проекта
1. В репозитории откройте терминал windows или PowerShell
2. В командной строке напишите и вставте текст: java -cp "lib/kotlinx-cli-0.2.1.jar:main.jar" MainKt (Ваш текст)
Пример: java -cp "lib/kotlinx-cli-0.2.1.jar:main.jar" MainKt -h

или

1. Запустите скрипт RUN.sh
2. В скрипте введите текст и вставьте

##  Тестирование
Для запуска тестов необходимо запустить скрипт TEST.sh



