@echo off
set /a num=0
  @echo 1 ���- ���� ���祭�� �१ ��ࠬ��� ��������� ��ப�
java -jar app.jar the quick brown fox jumps over the lazy dog > out.txt
fc /a out.txt true_out.txt
if ERRORLEVEL 1 (
  echo �訡��: 1 ��� �� �ன���
    echo ----------------------
) else (
  echo 1 ��� �ᯥ譮 �ன���
  echo ----------------------
  set /a num+=1
)

  @echo 2 ���- ���� ���祭�� �१ ��⮪ stdin
echo the quick brown fox jumps over the lazy dog| java -jar app.jar > out.txt
fc /a out.txt true_out.txt
if ERRORLEVEL 1 (
  echo �訡��: 2 ��� �� �ன���
    echo ----------------------
) else (
  echo 2 ��� �ᯥ譮 �ன���
  echo ----------------------
  set /a num+=1
)

  @echo 3 ���- ��� � ��ன(�㦭� ��ࠡ���� ��-��襬�)
echo the quick brown fox jumps over the lazy dog| java -jar app.jar > out.txt
fc /a out.txt true_out.txt
if ERRORLEVEL 1 (
  echo �訡��: 3 ��� �� �ன���
    echo ----------------------
) else (
  echo 3 ��� �ᯥ譮 �ன���
    echo ----------------------
  set /a num+=1
)
@echo �ன���� %num% ��⮢ �� 3-�



pause