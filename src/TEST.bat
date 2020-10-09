@echo off
set /a num=0
  @echo 1 тест- ввод значений через параметр командной строки
java -jar app.jar the quick brown fox jumps over the lazy dog > out.txt
fc /a out.txt true_out.txt
if ERRORLEVEL 1 (
  echo Ошибка: 1 тест не пройден
    echo ----------------------
) else (
  echo 1 тест успешно пройден
  echo ----------------------
  set /a num+=1
)

  @echo 2 тест- ввод значений через поток stdin
echo the quick brown fox jumps over the lazy dog| java -jar app.jar > out.txt
fc /a out.txt true_out.txt
if ERRORLEVEL 1 (
  echo Ошибка: 2 тест не пройден
    echo ----------------------
) else (
  echo 2 тест успешно пройден
  echo ----------------------
  set /a num+=1
)

  @echo 3 тест- как и второй(нужно доработать по-хорошему)
echo the quick brown fox jumps over the lazy dog| java -jar app.jar > out.txt
fc /a out.txt true_out.txt
if ERRORLEVEL 1 (
  echo Ошибка: 3 тест не пройден
    echo ----------------------
) else (
  echo 3 тест успешно пройден
    echo ----------------------
  set /a num+=1
)
@echo Пройдено %num% тестов из 3-х



pause