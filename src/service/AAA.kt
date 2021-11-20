package service

import dao.AAAEloquent
import models.RoleResource
import models.Roles
import models.User
import models.ExitCodeEnum
import java.math.BigInteger
import java.security.MessageDigest
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import models.Parser

class AAA {

    private val datePattern = "[\\d]{4}-(0[1-9]|1[012])-(0[1-9]|1[\\d]|2[\\d]|3[01])"

    /**
     *  функция аутентификации, авторизация, аккаунтинга
     */
    fun funAAA(parser: Parser): Int {
        // данные аутентификации
        val dataUser = User(
            parser.login,
            parser.pass
        )
        // валидность логина
        if (!isLoginValid(dataUser.login)) {
            return ExitCodeEnum.SECOND.printExitCode()
        }

        val eloquentAAA = AAAEloquent()

        // есть ли логин в БД
        if (!eloquentAAA.hasLogin(dataUser.login)) {
            return ExitCodeEnum.THREE.printExitCode()
        }

        // аутентификация
        if (!isAuthentication(dataUser)) {
            return ExitCodeEnum.FOUR.printExitCode()
        }

        // проверка на наличие роли и ресурса, если их нет, то просто успешная аутентификация, тк вверху уже прошла
        if (parser.inputRole != "null" && parser.res != "null") {
            // проверка на существование роли
            Roles.values().find { it.name == parser.inputRole } ?: return ExitCodeEnum.FIVE.printExitCode()

            val role = Roles.valueOf(parser.inputRole)

            // Данные авторизация
            val dataRoleResource = RoleResource(
                resource = parser.res.uppercase(),
                role = role,
            )

            // Авторизация
            if (isAuthorization(dataUser, dataRoleResource)) {
                if (parser.ds != "null" && parser.de != "null" && parser.vol != "null") {
                    if (this.isDateAndValueValid(parser.ds, parser.de, parser.vol)) {
                        return ExitCodeEnum.SEVEN.printExitCode()
                    }

                    this.checkDateAndValues(parser.ds, parser.de, parser.vol)
                } else {
                    return ExitCodeEnum.ZERO.printExitCode()// если не содержит дат, объема
                }
            } else {
                return ExitCodeEnum.SIX.printExitCode()
            }
        }
        return ExitCodeEnum.ZERO.printExitCode()
    }

    private fun isDateAndValueValid(ds: String, de: String, value: String): Boolean {
        if (Regex(this.datePattern).matches(ds)
            && Regex(this.datePattern).matches(de)
            && Regex("\\d+").matches(value)
        ) {
            val dateStart = LocalDate.parse(ds, DateTimeFormatter.ISO_DATE)
            val dateEnd = LocalDate.parse(de, DateTimeFormatter.ISO_DATE)
            return false
        }
        return true
    }

    /**
     * вернет истину, если логин валидный
     */
    private fun isLoginValid(login: String): Boolean {
        return Regex("^[A-Za-z0-9]{1,20}$").find(login) != null
    }

    /**
     * Вернет истину если успешно
     */
    private fun isAuthentication(dataUser: User): Boolean {
        val eloquentAAA = AAAEloquent()

        val inputUser = eloquentAAA.findUserByLogin(dataUser.login) ?: return false

        return inputUser.pass == getPassHashAndSalt(dataUser.pass, inputUser.salt)
    }

    /**
     * Вернет истину если успешно
     */
    private fun isAuthorization(dataUser: User, dataRoleResource: RoleResource): Boolean {
        val eloquentAAA = AAAEloquent()

        return eloquentAAA.isCheckResourceAccess(
            dataRoleResource.resource,
            dataRoleResource.role,
            dataUser.login
        )
    }

    /**
     * Вернет хэшированный пароль (с добавлением соли)
     */
    private fun getPassHashAndSalt(pass: String, salt: String): String {
        return getHash(getHash(pass) + salt)
    }

    /**
     * Вернет хэш строки (MD5)
     */
    private fun getHash(password: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(password.toByteArray())).toString(16).padStart(32, '0')
    }
}

