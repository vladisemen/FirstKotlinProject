package project_auth

import project_auth.models.RoleResource
import project_auth.models.Roles
import project_auth.models.User
import java.math.BigInteger
import java.security.MessageDigest

fun main(args: Array<String>) {
    val text = if (args.isEmpty()) {
        readLine().toString()
    } else {
        args.toString()
    }

    if (text == "" || text.contains("-h")) {
        exitCode(1)
    }

    val collectionParameter: MutableMap<String, String> = mutableMapOf()

    for (parameterAndValue in text.split("-")) {
        if (parameterAndValue != "") {
            val parameter = parameterAndValue.split(" ")
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

    if(isAuthentication(dataUser)){
        println("Аутентификация успешна")
        exitCode(0)
    }else{
        println("Аутентификация не успешна")
        exitCode(4)
    }

    val dataRoleResource = RoleResource(
        roleStringToEnum(collectionParameter.getValue("role")),
        collectionParameter.getValue("res"),
    )
    if (isAuthorization(dataUser, dataRoleResource)){
        println("Авторизация успешна")
        exitCode(0)
    }else{
        println("Авторизация не успешна")
        exitCode(6)
    }
}

fun isAuthentication(dataUser: User): Boolean {
    val userDB = DateBase()
    val inputUser = userDB.findUserByLogin(dataUser.login)

    if (inputUser == null) {
        exitCode(3)
        return false
    }
    return inputUser.pass == getPassHashAndSolt(dataUser.pass, inputUser.salt)
}

fun isAuthorization(dataUser: User, dataRoleResource: RoleResource):Boolean {
    val userDB = DateBase()
    if (dataRoleResource.role !== null) {
        return userDB.checkResourceAccess(dataRoleResource.resource, dataRoleResource.role, dataUser.login)
    }
    return false
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

fun exitCode(number: Int) {
    when (number) {
        0 -> {
            println("успех")
            System.exit(0)
        }
        1 -> {
            println("вывод справки")
            System.exit(1)
        }
        2 -> {
            println("неверный формат логина")
            System.exit(2)
        }
        3 -> {
            println("неверный логин")
            System.exit(3)
        }
        4 -> {
            println("неверный пароль")
            System.exit(4)
        }
        5 -> {
            println("неизвестная роль")
            System.exit(5)
        }
        6 -> {
            println("нет доступа")
            System.exit(6)
        }
        7 -> {
            println("некорректная активность")
            System.exit(7)
        }
    }
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