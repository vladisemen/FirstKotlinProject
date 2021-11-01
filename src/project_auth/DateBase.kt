package project_auth

import project_auth.models.RoleResource
import project_auth.models.Roles
import project_auth.models.User

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
        User(1, "admin", "35d0239415e2371ee283a773f215c036", "Salt"), // 123
        User(2, "user1", "b614d9bc7599d324e730dafbee318881", "Salt1")   // qwerty123
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

}