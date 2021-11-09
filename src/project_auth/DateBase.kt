package project_auth

import project_auth.Models.RoleResource
import project_auth.Models.Roles
import project_auth.Models.User

class DateBase {
    private val rolesResources: List<RoleResource> = listOf(
        //region admin
        RoleResource(
            Roles.READ,
            "A",
            1
        ),
        RoleResource(
            Roles.READ,
            "B",
            1
        ),
        RoleResource(
            Roles.READ,
            "C",
            1
        ),
        RoleResource(
            Roles.WRITE,
            "A",
            1
        ),
        RoleResource(
            Roles.WRITE,
            "B",
            1
        ),
        RoleResource(
            Roles.WRITE,
            "C",
            1
        ),
        RoleResource(
            Roles.EXECUTE,
            "A",
            1
        ),
        RoleResource(
            Roles.EXECUTE,
            "B",
            1
        ),
        RoleResource(
            Roles.EXECUTE,
            "C",
            1
        ),
        //endregion
        //region user
        RoleResource(
            Roles.READ,
            "A",
            2
        ),
        RoleResource(
            Roles.EXECUTE,
            "A.B",
            2
        ),
        RoleResource(
            Roles.WRITE,
            "XY.UV.ABCDEFGHIJ",
            2
        ),
        //endregion
    )
    private val users: List<User> = listOf(
        User("admin", "35d0239415e2371ee283a773f215c036", 1, "Salt"), // 123
        User("user1", "b614d9bc7599d324e730dafbee318881", 2, "Salt1")   // qwerty123
    )

    /**
     * Есть ли такой логин в БД?
     */
    fun hasLogin(login: String): Boolean {
        return findUserByLogin(login) != null
    }

    /**
     * найдет и вернет пароль в "БД"
     */
    fun findPasswordByLogin(login: String): String {
        return findUserByLogin(login)!!.pass
    }

    /**
     * найдет и вернет соль по логину
     */
    fun findSaltByLogin(login: String): String {
        return findUserByLogin(login)!!.salt
    }

    /**
     * Найдет и вернет юзера по логину
     */
    fun findUserByLogin(login: String): User? {
        return users.find { it.login == login }
    }

    /**
     * Имеет ли доступ к ресурсу
     */
    fun checkResourceAccess(resource: String, role: Roles, loginUser: String): Boolean {
        val idUser = findUserByLogin(loginUser)!!.id
        for (item in rolesResources) {
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