import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import models.RoleResource
import models.Roles
import models.User
import models.ExitCodeEnum
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

    val numberCode = isAAAFun(
        login.toString(),
        pass.toString(),
        res.toString(),
        inputRole.toString(),
        ds.toString(),
        de.toString(),
        vol.toString()
    )

    exitCode(numberCode)
}

/**
 *  функция аутентификации, авторизация, аккаунтинга
 */
fun isAAAFun(
    login: String,
    pass: String,
    res: String,
    inputRole: String,
    ds: String,
    de: String,
    vol: String,
): Int {
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
        login,
        pass
    )
    // валидность логина
    if (!isLoginValid(dataUser.login)) {
        return 2
    }

    val dateBase = DateBase()
    // есть ли логин в БД
    if (!dateBase.hasLogin(dataUser.login)) {
        return 3
    }

    // аутентификация
    if (!isAuthentication(dataUser)) {
        return 4
    }
    // запись данных аутентифицированного пользователя
    //File("./src/project_auth/accounting.txt")
    //  .bufferedWriter().use { out -> out.write(login + " " + pass) }

    // проверка на наличие роли и ресурса, если их нет, то просто успешная аутентификация, тк вверху уже прошла
    if (inputRole != "null" && res != "null") {
        val role = Roles.valueOf(inputRole) ?: return 5 // затестить проверку на корректность роли

        // Данные авторизация
        val dataRoleResource = RoleResource(
            role,
            res.uppercase(),
        )

        // Авторизация
        if (isAuthorization(dataUser, dataRoleResource)) {
            if (ds != "null" && de != "null" && vol != "null") {
                if (isDateAndValueValid(ds, de, vol)) {
                    return 7
                }

                checkDateAndValues(ds, de, vol)
            } else {
                return 0// если не содержит дат, объема
            }
        } else {
            return 6
        }
    }
    return 0
}

fun isDateAndValueValid(ds: String, de: String, value: String): Boolean {
    val datePattern = "[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])"

    if (Regex(datePattern).matches(ds)
        || Regex(datePattern).matches(de)
        || Regex("\\d+").matches(value)
    ) {
        return true
    }
    return false
}

fun checkDateAndValues(ds: String, de: String, value: String) {
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
    val inputUser = userDB.findUserByLogin(dataUser.login) ?: return false

    return inputUser.pass == getPassHashAndSalt(dataUser.pass, inputUser.salt)
}

/**
 * Вернет истину если успешно
 */
fun isAuthorization(dataUser: User, dataRoleResource: RoleResource): Boolean {
    val userDB = DateBase()

    return userDB.checkResourceAccess(dataRoleResource.resource, dataRoleResource.role, dataUser.login)
}

/**
 * Вернет хэшированный пароль (с добавлением соли)
 */
fun getPassHashAndSalt(pass: String, salt: String): String {
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
        0 -> println(ExitCodeEnum.ZERO.text)
        1 -> println(ExitCodeEnum.FIRST.text)
        2 -> println(ExitCodeEnum.SECOND.text)
        3 -> println(ExitCodeEnum.THREE.text)
        4 -> println(ExitCodeEnum.FOUR.text)
        5 -> println(ExitCodeEnum.FIVE.text)
        6 -> println(ExitCodeEnum.SIX.text)
        7 -> println(ExitCodeEnum.SEVEN.text)
    }

    exitProcess(number)
}

