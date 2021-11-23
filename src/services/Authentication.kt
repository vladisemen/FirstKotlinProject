package services

import dao.AAAEloquent
import models.User
import models.ExitCodeEnum
import java.math.BigInteger
import java.security.MessageDigest
import models.Parser

class Authentication(_parser: Parser) {

    private val parser: Parser

    init {
        parser = _parser
    }

    /**
     *  функция аутентификации, авторизация, аккаунтинга
     */
    fun authentication(): Int {
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

        return ExitCodeEnum.SUCCESS.code
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

