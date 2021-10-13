fun main(args: Array<String>) {
    val list: Array<String> = if (args.isEmpty())
    {
        readLine().toString().split(" ").toTypedArray() //read stdin
    } else {
        args
    }
    list.sorted().groupingBy { it }.eachCount().toList()
        .sortedByDescending { (_, value) -> value }.toMap()
        .forEach{ (k, v) -> println("$k $v")}
}