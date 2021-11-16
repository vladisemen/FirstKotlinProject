package dao

import DateBase
import models.Roles
import models.User

/**
 * DAO для класса AAA
 */
class AAAEloquent {
    //подключение БД
    private val dateBase = DateBase()

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
        return dateBase.getUsers().find { it.login == login }
    }

    /**
     * Имеет ли доступ к ресурсу
     */
    fun isCheckResourceAccess(resource: String, role: Roles, loginUser: String): Boolean {
        val idUser = findUserByLogin(loginUser)!!.id

        for (item in dateBase.getRolesResources()) {
            if (item.idUser == idUser && item.role == role && item.resource.count() <= resource.count()) {
                if (resource.split(".").containsAll(item.resource.split("."))) {
                    return true
                }
            }
        }
        return false
    }
}