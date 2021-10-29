package project_auth

val Users: List<User> = listOf(
    User("admin", "123"),
    User("user1", "qwerty123")
)

fun main(args: Array<String>) {
    val list: Array<String> = if (args.isEmpty()) {
        readLine().toString().split(" ").toTypedArray() //read stdin
    } else {
        args
    }
    if (list.size > 1){
        val inputLogin = list[0]
        val inputPass = list[1]
        println(inputLogin)
        println(inputPass)
    }
}