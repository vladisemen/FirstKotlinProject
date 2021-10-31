package project_auth

import project_auth.models.RoleResource
import project_auth.models.User
import java.math.BigInteger
import java.security.MessageDigest

fun main(args: Array<String>) {
    val text = if (args.isEmpty()) {
        readLine().toString()
    } else {
        args.toString()
    }

    val collectionParameter: MutableMap<String, String> = mutableMapOf()

    for (parameterAndValue in text.split("-")) {
        if (parameterAndValue != ""){
            val parameter = parameterAndValue.toString().split(" ")
            collectionParameter.put(
                parameter[0],
                parameter[1]
            )
        }
    }

    val dataUser = User(
        collectionParameter.getValue("login"),
        collectionParameter.getValue("pass")
    )

    println(dataUser.login)
    println(dataUser.pass)
    val userDB = DateBase()
    val inputUser = userDB.findUserByLogin(dataUser.login)
    if (inputUser == null) {
        print("Пользователь не найден!")
        return
    }

    if (inputUser.pass == getPassHashAndSolt(dataUser.pass, inputUser.salt)) {
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