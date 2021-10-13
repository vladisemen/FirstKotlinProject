@echo off
set /a num=0
set TestDate=the quick brown fox jumps over the lazy dog
for /l %%i in (1,1,5) do (
	echo %%i test
	java -jar jar/task%%i.jar %TestDate% > out.txt
	fc /a out.txt true_out/task%%i.txt
	if ERRORLEVEL 1 (
	  echo !Error: %%i test failed!
	) else (
	  echo %%i test passed
	  set /a num+=1
	)
	  echo ----------------------
)

echo 6 test- input values throught stdin
echo %TestDate%| java -jar jar/task6.jar > out.txt

fc /a out.txt true_out/task6.txt
if ERRORLEVEL 1 (
  echo !Error: 6 test failed!
) else (
  echo 6 test passed
  set /a num+=1
)
  echo ----------------------

@echo Passed %num% tests from 6

pause