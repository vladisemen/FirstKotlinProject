fun main(args: Array<String>) {
    val mapCollection = args.sorted().groupingBy { it }.eachCount()
    printCollection(mapCollection)
}