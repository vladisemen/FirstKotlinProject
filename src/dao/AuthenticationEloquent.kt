package dao

import DateBase
import models.User

class AuthenticationEloquent(_login: String) {
    private val login: String

    init {
        login = _login
    }

    /**
     * Есть ли такой логин в БД?
     */
    fun hasLogin(): Boolean {
        return findUserByLogin() != null
    }

    /**
     * Найдет и вернет юзера по логину
     */
    fun findUserByLogin(): User? {
        return DateBase.getUsers().find { it.login == this.login }
    }
}