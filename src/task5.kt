fun main(args: Array<String>) {
    val result = args.sorted().groupingBy { it }.eachCount().toList().sortedByDescending { (_, value) -> value }.toMap()
    printCollection(result)
}

