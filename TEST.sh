TestDate="the quick brown fox jumps over the lazy dog"
Count=0
for i in 1 2 3 4 5 6
do

	printf "task$i test running...\n"
	java -jar jar/task$i.jar $TestDate > out.txt
if cmp -s out.txt true_out/task$i.txt ; then
 printf "success\n"
 Count=$((Count+1))
 else
 printf "fail\n"
fi
done
printf "Tests passed $Count from 6"
exit 1