fun main(args: Array<String>) {
    val list: Array<String> = if (args.isEmpty()) // если через параметр ничего не пришло, читаем поток stdin
    {
        readLine().toString().split(" ").toTypedArray()
    } else {
        args
    }
    
    println("Задание 1:")
    list.forEach(::println)

    println("Задание 2:")
    list.sorted().forEach(::println)

    println("Задание 3:")
    list.sorted().toSet().forEach(::println)

    println("Задание 4:")
    val mapCollection = list.sorted().groupingBy { it }.eachCount()
    printCollection(mapCollection)

    println("Задание 5:")
    val result = mapCollection.toList().sortedByDescending { (_, value) -> value }.toMap()
    printCollection(result)
}
fun printCollection(collection: Map<String, Int>) {
    for (item in collection) println("${item.key} ${item.value}")
}
