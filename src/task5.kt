fun main(args: Array<String>) {
    args.sorted().groupingBy { it }.eachCount()
        .toList().sortedByDescending { (_, value) -> value }.toMap()
        .forEach{ (k, v) -> println("$k $v")}
}

