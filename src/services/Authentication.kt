package services

import dao.AuthenticationEloquent
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
        // валидность логина
        if (!isLoginValid(parser.login)) {
            return ExitCodeEnum.INVALID_LOGIN_FORMAT.code
        }

        val eloquentAuth = AuthenticationEloquent(parser.login)

        // есть ли логин в БД
        if (!eloquentAuth.hasLogin()) {
            return ExitCodeEnum.INVALID_LOGIN.code
        }

        // аутентификация
        if (!isAuthentication(User(parser.login, parser.pass))) {
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
        val eloquentAuth = AuthenticationEloquent(dataUser.login)

        val inputUser = eloquentAuth.findUserByLogin() ?: return false

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

