for i in 1 2 3 4 5 6
do
 kotlinc src/task$i.kt -include-runtime -d jar/task$i.jar
done
