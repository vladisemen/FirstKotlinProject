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

class AAA(_parser: Parser) {

    private val parser: Parser

    init {
        parser = _parser
    }

    private val datePattern = "[0-9]{4}-[0-9]{2}-[0-9]{2}"

    /**
     *  функция аутентификации, авторизация, аккаунтинга
     */
    fun funAAA(): Int {
        // данные аутентификации
        val dataUser = User(
            parser.login,
            parser.pass
        )
        // валидность логина
        if (!isLoginValid(dataUser.login)) {
            return ExitCodeEnum.INVALID_LOGIN_FORMAT.code
        }

        val eloquentAAA = AAAEloquent()

        // есть ли логин в БД
        if (!eloquentAAA.hasLogin(dataUser.login)) {
            return ExitCodeEnum.INVALID_LOGIN.code
        }

        // аутентификация
        if (!isAuthentication(dataUser)) {
            return ExitCodeEnum.INVALID_PASSWORD.code
        }

        // проверка на наличие роли и ресурса, если их нет, то просто успешная аутентификация, тк вверху уже прошла
        if (parser.inputRole != "null" && parser.res != "null") {
            // проверка на существование роли
            Roles.values().find { it.name == parser.inputRole } ?: return ExitCodeEnum.UNKNOWN_ROLE.code

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
                        return ExitCodeEnum.INCORRECT_ACTIVITY.code
                    }

                    val dateStart = LocalDate.parse(parser.ds, DateTimeFormatter.ISO_DATE)
                    val dateEnd = LocalDate.parse(parser.de, DateTimeFormatter.ISO_DATE)
                } else {
                    return ExitCodeEnum.SUCCESS.code// если не содержит дат, объема
                }
            } else {
                return ExitCodeEnum.NO_ACCESS.code
            }
        }
        return ExitCodeEnum.SUCCESS.code
    }

    private fun isDateAndValueValid(ds: String, de: String, value: String): Boolean {
        if (Regex(this.datePattern).matches(ds)
            && Regex(this.datePattern).matches(de)
            && Regex("\\d+").matches(value)
        ) {
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

