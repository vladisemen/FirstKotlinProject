package project_auth

import java.math.BigInteger
import java.security.MessageDigest

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
    val inputLogin = list[0]
    val inputPass = list[1]
    println(inputLogin)
    println(inputPass)
    val inputUser = Users.find { it.login == inputLogin }
    if (inputUser == null) {
        print("Пользователь не найден!")
        return
    }
    if (inputUser.pass == inputPass) {
        print("Аутнетификация успешна")
    } else {
        print("Аутентификация неуспешна")
    }

}

/**
 * Вернет хэш строки (MD5)
 */
fun getHash(password: String): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(password.toByteArray())).toString(16).padStart(32, '0')
}