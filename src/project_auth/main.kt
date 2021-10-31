package project_auth

import project_auth.models.Roles
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
    val inputRoleStr = list[2]
    val inputRes = list[3]
    println(inputLogin)
    println(inputPass)
    val userDB = DateBase()
    val inputUser = userDB.findUserByLogin(inputLogin)
    if (inputUser == null) {
        println("Пользователь не найден!")
        return
    }
    val inputRole = roleStringToEnum(inputRoleStr)
    if (inputUser.pass == getPassHashAndSolt(inputPass, inputUser.salt) && inputRole !== null ) {
        println("Аутнетификация успешна")
        if(userDB.checkResourceAccess(inputRes, inputRole, inputLogin)){
            println("Есть Доступ!")
        }else{
            println("Нету доступа!")
        }
    } else {
        println("Аутентификация неуспешна")
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
/**
 * Вернет роль типа Enum если соотв, иначе вернет null
 */
fun roleStringToEnum(roleString: String): Roles? {
    return when (roleString) {
        "READ" -> {
            Roles.READ
        }
        "WRITE" -> {
            Roles.WRITE
        }
        "EXECUTE" -> {
            Roles.EXECUTE
        }
        else -> {
            null
        }
    }
}