fun main(args: Array<String>) {
    //region 6 задание
    val list: Array<String> = if (args.isEmpty()) // если через параметр ничего не пришло, читаем поток stdin
    {
        readLine().toString().split(" ").toTypedArray()
    } else {
        args
    }
    //endregion
    //region 1 задание
    println("Задание 1:")
    list.forEach(::println)
    //endregion
    //region 2 задание
    println("Задание 2:")
    list.sorted().forEach(::println)
    //endregion
    //region 3 задание
    println("Задание 3:")
    list.sorted().toSet().forEach(::println)
    //endregion
    //region 4 задание
    println("Задание 4:")
    val mapCollection = list.sorted().groupingBy { it }.eachCount()
    for (i in mapCollection) {
        println(i.key + " " + i.value)
    }
    //endregion
    //region 5 задание
    val result = mapCollection.toList().sortedByDescending { (_, value) -> value }.toMap()
    println("Задание 5:")
    for (i in result) {
        println(i.key + " " + i.value)
    }
    //endregion
}
