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
import kotlin.system.exitProcess
import models.Parser

class AAA {
    /**
     *  функция аутентификации, авторизация, аккаунтинга
     */
    fun isAAAFun(parser: Parser): Int {
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
            parser.login,
            parser.pass
        )
        // валидность логина
        if (!isLoginValid(dataUser.login)) {
            return 2
        }

        val isAAAEloquent = AAAEloquent()

        // есть ли логин в БД
        if (!isAAAEloquent.hasLogin(dataUser.login)) {
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
        if (parser.inputRole != "null" && parser.res != "null") {
            // проверка на существование роли
            Roles.values().find { it.name == parser.inputRole } ?: return 5

            val role = Roles.valueOf(parser.inputRole)

            // Данные авторизация
            val dataRoleResource = RoleResource(
                role,
                parser.res.uppercase(),
            )

            // Авторизация
            if (isAuthorization(dataUser, dataRoleResource)) {
                if (parser.ds != "null" && parser.de != "null" && parser.vol != "null") {
                    if (this.isDateAndValueValid(parser.ds, parser.de, parser.vol)) {
                        return 7
                    }

                    this.checkDateAndValues(parser.ds, parser.de, parser.vol)
                } else {
                    return 0// если не содержит дат, объема
                }
            } else {
                return 6
            }
        }
        return 0
    }

    private fun isDateAndValueValid(ds: String, de: String, value: String): Boolean {
        val datePattern = "[0-9]{4}-(0[1-9]|1[012])-(0[1-9]|1[0-9]|2[0-9]|3[01])"

        if (Regex(datePattern).matches(ds)
            && Regex(datePattern).matches(de)
            && Regex("\\d+").matches(value)
        ) {
            return false
        }
        return true
    }

    private fun checkDateAndValues(ds: String, de: String, value: String) {
        val dateStart = LocalDate.parse(ds, DateTimeFormatter.ISO_DATE)
        val dateEnd = LocalDate.parse(de, DateTimeFormatter.ISO_DATE)
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
        val isAAAEloquent = AAAEloquent()

        val inputUser = isAAAEloquent.findUserByLogin(dataUser.login) ?: return false

        return inputUser.pass == getPassHashAndSalt(dataUser.pass, inputUser.salt)
    }

    /**
     * Вернет истину если успешно
     */
    private fun isAuthorization(dataUser: User, dataRoleResource: RoleResource): Boolean {
        val isAAAEloquent = AAAEloquent()

        return isAAAEloquent.checkResourceAccess(
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
}

