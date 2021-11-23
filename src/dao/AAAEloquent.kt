package dao

import DateBase
import models.Roles
import models.User

class AAAEloquent(_login: String, _resource: String = "", _role: Roles = Roles.READ) {

    private val login: String
    private val resource: String
    private val role: Roles

    init {
        login = _login
        resource = _resource
        role = _role
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

    /**
     * Имеет ли доступ к ресурсу
     */
    fun isCheckResourceAccess(): Boolean {
        val idUser = findUserByLogin()!!.id
        for (item in DateBase.getRolesResources(role, idUser, resource.count())) {
            val lengthDateRes = item.resource.length
            if (item.resource == resource.substring(0, lengthDateRes)
                && (lengthDateRes == resource.length || resource[lengthDateRes] == '.')
            ) {
                return true
            }
        }
        return false
    }
}