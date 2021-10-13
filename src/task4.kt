fun main(args: Array<String>) {
    args.sorted().groupingBy { it }.eachCount()
        .forEach{ (k, v) -> println("$k $v")}
}