package project_auth

import project_auth.models.RoleResource
import project_auth.models.Roles
import project_auth.models.User
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val inputText = if (args.isEmpty()) {
        readLine().toString()
    } else {
        args.toString()
    }
    // есть ли справка или строка пуста
    if (inputText == "" || inputText.contains("-h")) {
        exitCode(1)
    }
    val collectionParameter: MutableMap<String, String> = mutableMapOf()
    writeParameters(collectionParameter, inputText)

    // есть ли логин и пароль
    if (!collectionParameter.containsKey("login") || !collectionParameter.containsKey("pass")) {
        exitCode(1)
    }

    val dataUser = User(
        collectionParameter.getValue("login"),
        collectionParameter.getValue("pass")
    )
    // валидность логина
    if (!isLoginValid(dataUser.login)) {
        exitCode(3)
    }
    val dateBase = DateBase()
    // есть ли логин в БД
    if (!dateBase.hasLogin(dataUser.login)) {
        exitCode(3)
    }

    // аутентификация
    if (!isAuthentication(dataUser)) {
        exitCode(4)
    }
    // проверка на наличие роли и ресурса, если их нет, то просто успешная аутентификация, тк вверху уже прошла
    if (!collectionParameter.containsKey("role") || !collectionParameter.containsKey("res")) {
        exitCode(0)
    }
    val role = roleStringToEnum(collectionParameter.getValue("role"))
    if (role == null){
        exitCode(5)
    }
    // авторизация
    val dataRoleResource = RoleResource(
        role,
        collectionParameter.getValue("res"),
    )
    if (isAuthorization(dataUser, dataRoleResource)) {
        exitCode(0)
    } else {
        exitCode(6)
    }
}

/**
 * вернет истину, если логин валидный
 */
fun isLoginValid(login: String): Boolean {
    return login.length <= 20
}

/**
 * Заполнение параметров словаря
 */
fun writeParameters(collectionParameter: MutableMap<String, String>, inputText: String) {
    for (parameterAndValue in inputText.split("-")) {
        if (parameterAndValue != "") {
            val parameter = parameterAndValue.split(" ")
            collectionParameter[parameter[0]] = parameter[1]
        }
    }
}

/**
 * Вернет истину если успешно
 */
fun isAuthentication(dataUser: User): Boolean {
    val userDB = DateBase()
    val inputUser = userDB.findUserByLogin(dataUser.login)

    if (inputUser == null) {
        exitCode(3)
        return false
    }
    return inputUser.pass == getPassHashAndSolt(dataUser.pass, inputUser.salt)
}

/**
 * Вернет истину если успешно
 */
fun isAuthorization(dataUser: User, dataRoleResource: RoleResource): Boolean {
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
            exitProcess(0)
        }
        1 -> {
            println("вывод справки")
            exitProcess(1)
        }
        2 -> {
            println("неверный формат логина")
            exitProcess(2)
        }
        3 -> {
            println("неверный логин")
            exitProcess(3)
        }
        4 -> {
            println("неверный пароль")
            exitProcess(4)
        }
        5 -> {
            println("неизвестная роль")
            exitProcess(5)
        }
        6 -> {
            println("нет доступа")
            exitProcess(6)
        }
        7 -> {
            println("некорректная активность")
            exitProcess(7)
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