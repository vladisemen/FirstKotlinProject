md jar
for /l %%i in (1,1,6) do kotlinc src/task%%i.kt src/functions.kt -include-runtime -d jar/task%%i.jar

::It doesn't work
CALL kotlinc clean install
pause