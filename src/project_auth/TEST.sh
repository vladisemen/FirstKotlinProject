QuantityTest=36
QuantitPassedTests=0
declare -A CaseTest
CaseTest[0]=""
CaseTest[1]="-h"
CaseTest[2]="-login admin -pass 123"
CaseTest[3]="-pass 123 -login admin"
CaseTest[4]="-login !admin! -pass 123"
CaseTest[5]="-login asd -pass dsa"
CaseTest[6]="-login admin -pass 0000"
CaseTest[7]="-login admin -pass 123 -role READ -res A"
CaseTest[8]="-login admin -pass 123 -role qwe -res A"
CaseTest[9]="-login q -pass ytrewq -role EXECUTE -res A"
CaseTest[10]="-login admin -pass 123 -role READ -res A.B"
CaseTest[11]="-login admin -pass 123 -role WRITE -res A.B.C"
CaseTest[12]="-login admin -pass 123 -role qwe -res A.B.C"
CaseTest[13]="-login admin -pass 123 -role WRITE -res A.B"
CaseTest[14]="-login admin -pass 123 -role READ -res A.B -ds 2020-01-11 -de 2020-01-12 -vol 10"
CaseTest[15]="-login admin -pass 123 -role READ -res A.B -ds 2020.01.11 -de 2020.01.12 -vol 10"
CaseTest[16]="-login admin -pass 123 -role READ -res A.B -ds 2020-01-1 -de 2020-01-60 -vol 10"
CaseTest[17]="-login admin -pass 123 -role READ -res A.B -ds 2020-01-11 -de 2020-10-12 -vol hgh"
CaseTest[18]="-login admin -pass 123 -role WRITE -res A -ds 2020-01-12 -de 2020-01-13 -vol 10"

CaseTest[19]=""
CaseTest[20]="-h"
CaseTest[21]="-login X-X -pass XXX"
CaseTest[22]="-login XXX -pass XXX"
CaseTest[23]="-login jdoe -pass XXX"
CaseTest[24]="-login jdoe -pass sup3rpaZZ"
CaseTest[25]="-login jdoe -pass sup3rpaZZ -role READ -res a"
CaseTest[26]="-login jdoe -pass sup3rpaZZ -role READ -res a.b"
CaseTest[27]="-login jdoe -pass sup3rpaZZ -role XXX -res a.b"
CaseTest[28]="-login jdoe -pass sup3rpaZZ -role READ -res XXX"
CaseTest[29]="-login jdoe -pass sup3rpaZZ -role WRITE -res a"
CaseTest[30]="-login jdoe -pass sup3rpaZZ -role WRITE -res a.bc"
CaseTest[31]="-login jdoe -pass sup3rpaZZ -role READ -res a.b -ds 2015-01-01 -de 2015-12-31 -vol 100"
CaseTest[32]="-login jdoe -pass sup3rpaZZ -role READ -res a.b -ds 01-01-2015 -de 2015-12-31 -vol 100"
CaseTest[33]="-login jdoe -pass sup3rpaZZ -role READ -res a.b -ds 2015-01-01 -de 2015-12-31 -vol XXX"
CaseTest[34]="-login X -pass X -role READ -res X -ds 2015-01-01 -de 2015-12-31 -vol XXX"
CaseTest[35]="-login X -pass X -role READ -res X"

expectedExitCodes=(1 0 0 0 2 3 4 0 5 3 0 0 5 0 0 7 7 7 0 1 0 2 3 4 0 0 0 5 6 6 6 0 7 7 3 3)

for ((i = 0; i < "$QuantityTest"; i++)); do
  test=${CaseTest[$i]}
  expectedExitCode=${expectedExitCodes[$i]}
  java -cp "./src/project_auth/lib/kotlinx-cli-0.2.1.jar:./src/project_auth/main.jar" project_auth.MainKt ""${test}""
  exitCode="$?"
  if [ "$exitCode" == "$expectedExitCode" ]; then
    echo "CaseTest $i passed exit code $exitCode"
    let QuantitPassedTests++
  else
    echo "CaseTest $i error incoming $exitCode expected - $expectedExitCode."
  fi
done
echo "Result: $QuantitPassedTests completed tests out of $QuantityTest"

if [ "$QuantitPassedTests" == "$QuantityTest" ]; then
  exit 0
else
  exit 1
fi
pause
