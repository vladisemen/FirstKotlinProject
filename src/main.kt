import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import project_auth.Models.RoleResource
import project_auth.Models.Roles
import project_auth.Models.User
import java.math.BigInteger
import java.security.MessageDigest
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.system.exitProcess

fun main(args: Array<String>) {

    // разбиение данных на параметры
    val parser = ArgParser("example")

    val login by parser.option(ArgType.String, shortName = "login", description = "Login")
    val pass by parser.option(ArgType.String, shortName = "pass", description = "Password")
    val res by parser.option(ArgType.String, shortName = "res", description = "Resource")
    val inputRole by parser.option(ArgType.String, shortName = "role", description = "Role")
    val ds by parser.option(ArgType.String, shortName = "ds", description = "Date start")
    val de by parser.option(ArgType.String, shortName = "de", description = "Date finish")
    val vol by parser.option(ArgType.String, shortName = "vol", description = "Number")

    parser.parse(args)

/*    //      аккаунтинг
    // есть ли логин и пароль
    if (login == null || pass == null) {
        // проверка на аутентификации ранее
        val paramAuth = File("./src/project_auth/accounting.txt").bufferedReader().use { it.readLine() }
            .split(" ")

        if (paramAuth.count() == 2) {
            login = paramAuth[0]
            pass = paramAuth[1]
        } else {
            exitCode(1)
        }
    }*/

    // данные аутентификации
    val dataUser = User(
        login.toString(),
        pass.toString()
    )
    // валидность логина
    if (!isLoginValid(dataUser.login)) {
        exitCode(2)
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
    // запись данных аутентифицированного пользователя
    //File("./src/project_auth/accounting.txt")
    //  .bufferedWriter().use { out -> out.write(login + " " + pass) }

    // проверка на наличие роли и ресурса, если их нет, то просто успешная аутентификация, тк вверху уже прошла
    if (inputRole != null && res != null) {
        val role = roleStringToEnum(inputRole.toString())

        if (role == null) {
            exitCode(5)
        }

        // Данные авторизация
        val dataRoleResource = RoleResource(
            role,
            res.toString().uppercase(),
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

fun isDateAndValueValid(ds: String, de: String, value: String) {
    val datePattern = "[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])"

    if (Regex(datePattern).find(ds) == null
        || Regex(datePattern).find(de) == null
        || Regex("\\d+").find(value) == null
    ) {
        exitCode(7)
    }
}

fun checkDateAndValues(ds: String, de: String, value: String) {
    isDateAndValueValid(ds, de, value)

    val dateStart = LocalDate.parse(ds, DateTimeFormatter.ISO_DATE)
    val dateEnd = LocalDate.parse(de, DateTimeFormatter.ISO_DATE)
}

/**
 * вернет истину, если логин валидный
 */
fun isLoginValid(login: String): Boolean {
    return Regex("^[A-Za-z0-9]{1,20}$").find(login) != null
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