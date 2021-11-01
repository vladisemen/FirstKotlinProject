package project_auth

import java.math.BigInteger
import java.security.MessageDigest

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
    val userDB = DateBase()
    val inputUser = userDB.findUserByLogin(inputLogin)
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