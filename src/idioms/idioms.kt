package idioms

data class Customer(val name: String, val email: String) // it has getter & setter

//it's single fun
fun String.AddGoodWord() = this + " good word"

fun transform(color: String): Int {
    return when (color) {
        "Red" -> 0
        "Green" -> 1
        "Blue" -> 2
        else -> throw IllegalArgumentException("Invalid color param value")
    }
}


fun main() {

    // filter a list
    val list1: List<Int> = listOf(-2, -1, 0, 1, 2)
    val newList = list1.filter { x -> x > 0 }
    println(newList)
    print(list1.filter { it > 0 }) //  same
    print("")

    // lazy property
    val lazyProp: String by lazy {
        print("It's made a 1 time\n")
        "It's the forever thing\n"
    }
    print(lazyProp)
    print(lazyProp)
    print(lazyProp)

    //  extention fun
    print("adgv".AddGoodWord())

    print(transform("Red"))


}
