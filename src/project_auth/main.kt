package project_auth

import project_auth.models.User
import java.math.BigInteger
import java.security.MessageDigest

val Users: List<User> = listOf(
    User("admin", "35d0239415e2371ee283a773f215c036", "Salt"), // 123
    User("user1", "b614d9bc7599d324e730dafbee318881", "Salt1")   // qwerty123
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

    if (inputUser.pass == getPassHashAndSolt(inputPass, inputUser.salt)) {
        print("Аутнетификация успешна")
    } else {
        print("Аутентификация неуспешна")
    }

}

/**
 * Вернет хэшированный пароль (с добавлением соли)
 */
fun getPassHashAndSolt(pass: String, salt: String): String {
    return getHash(getHash(pass) + salt)
}

/**
 * Вернет хэш строки (MD5)
 */
fun getHash(password: String): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(password.toByteArray())).toString(16).padStart(32, '0')
}