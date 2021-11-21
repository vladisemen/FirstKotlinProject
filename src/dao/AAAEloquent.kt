package dao

import DateBase
import models.Roles
import models.User

/**
 * DAO для класса AAA
 */
class AAAEloquent {

    /**
     * Есть ли такой логин в БД?
     */
    fun hasLogin(login: String): Boolean {
        return findUserByLogin(login) != null
    }

    /**
     * Найдет и вернет юзера по логину
     */
    fun findUserByLogin(login: String): User? {
        return DateBase.getUsers().find { it.login == login }
    }

    /**
     * Имеет ли доступ к ресурсу
     */
    fun isCheckResourceAccess(resource: String, role: Roles, loginUser: String): Boolean {
        val idUser = findUserByLogin(loginUser)!!.id
        for (item in DateBase.getRolesResources(role, idUser, resource.count())) {
            val lengthDateRes = item.resource.length
            if (item.resource == resource.substring(0,lengthDateRes) && (lengthDateRes == resource.length) || resource[lengthDateRes] == '.') {
                    return true
                }
        }
        return false
    }

}