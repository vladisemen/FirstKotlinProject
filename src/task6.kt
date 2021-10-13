fun main(args: Array<String>) {
    val list: Array<String> = if (args.isEmpty())
    {
        readLine().toString().split(" ").toTypedArray() //read stdin
    } else {
        args
    }

    println("requirement 1:")
    list.forEach(::println)

    println("requirement 2:")
    list.sorted().forEach(::println)

    println("requirement 3:")
    list.sorted().distinct().forEach(::println)

    println("requirement 4:")
    val mapCollection = list.sorted().groupingBy { it }.eachCount()
    printCollection(mapCollection)

    println("requirement 5:")
    val result = mapCollection.toList().sortedByDescending { (_, value) -> value }.toMap()
    printCollection(result)
}