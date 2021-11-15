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
        return dateBase.users.find { it.login == login }
    }

    /**
     * Имеет ли доступ к ресурсу
     */
    fun checkResourceAccess(resource: String, role: Roles, loginUser: String): Boolean {
        val idUser = findUserByLogin(loginUser)!!.id

        for (item in dateBase.rolesResources) {
            if (item.idUser == idUser && item.role == role && isResource(resource, item.resource)) {
                return true
            }
        }
        return false
    }

    /**
     * Получает исходный ресурс и полученный и проверяет доступность
     */
    private fun isResource(resource: String, itemResource: String): Boolean {
        val resourceList = resource.split(".")
        val itemResourceList = itemResource.split(".")

        if (itemResourceList.count() > resourceList.count()) {
            return false
        }

        for (i in 0 until itemResourceList.count()) {
            if (itemResourceList[i] != resourceList[i]) {
                return false
            }
        }
        return true
    }
}