read -p "Введите текст: " text
java -cp "lib/kotlinx-cli-0.2.1.jar:lib/h2-1.4.200.jar:lib/flyway-core-8.2.1.jar:lib/log4j-1.2.17.jar:main.jar" MainKt $text
sleep 120s