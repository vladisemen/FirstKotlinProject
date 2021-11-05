package project_auth

import project_auth.models.RoleResource
import project_auth.models.Roles
import project_auth.models.User
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.system.exitProcess
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlinx.cli.*

fun main(args: Array<String>) {
    val inputText: Array<String> = if (args.isEmpty()) {
        readLine().toString().split(" ").toTypedArray()
    } else {
        args
    }

    // разбиение данных на параметры
    val parser = ArgParser("example")

    val login by parser.option(ArgType.String, shortName = "login", description = "Login")
    val pass by parser.option(ArgType.String, shortName = "pass", description = "Password")
    val res by parser.option(ArgType.String, shortName = "res", description = "Resource")
    val inputRole by parser.option(ArgType.String, shortName = "role", description = "Role")
    val ds by parser.option(ArgType.String, shortName = "ds", description = "Date start")
    val de by parser.option(ArgType.String, shortName = "de", description = "Date finish")
    val vol by parser.option(ArgType.Int, shortName = "vol", description = "Number")

    parser.parse(inputText)

    // есть ли логин и пароль
    if (login == null || pass == null) {
        exitCode(1)
    }

    // данные аутентификации
    val dataUser = User(
        login.toString(),
        pass.toString()
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
    if (inputRole != null && res != null) {
        val role = roleStringToEnum(inputRole.toString())

        if (role == null) {
            exitCode(5)
        }

        // Данные авторизация
        val dataRoleResource = RoleResource(
            role,
            res.toString(),
        )

        // Авторизация
        if (isAuthorization(dataUser, dataRoleResource)) {
            if (ds != null && de != null && vol != null) {
                checkDateAndValues(ds.toString(), de.toString(), vol.toString())
            } else {
                exitCode(0)// если не содержит дат, объема
            }
        } else {
            exitCode(6)
        }
    }
    exitCode(0)
}

fun checkDateAndValues(ds: String, de: String, value: String) {
    val dateStart = LocalDate.parse(ds, DateTimeFormatter.ISO_DATE)
    val dateEnd = LocalDate.parse(de, DateTimeFormatter.ISO_DATE)
}

/**
 * вернет истину, если логин валидный
 */
fun isLoginValid(login: String): Boolean {
    return (Regex("^[a-zA-Z0-9]").find(login) != null) && (login.length <= 20)
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