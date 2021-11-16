import models.RoleResource
import models.Roles
import models.User

class DateBase {
    private val rolesResources: List<RoleResource> = listOf(
        //region admin
        RoleResource(Roles.READ, "A", 1),
        RoleResource(Roles.READ, "B", 1),
        RoleResource(Roles.READ, "C", 1),
        RoleResource(Roles.WRITE, "A", 1),
        RoleResource(Roles.WRITE, "B", 1),
        RoleResource(Roles.WRITE, "C", 1),
        RoleResource(Roles.EXECUTE, "A", 1),
        RoleResource(Roles.EXECUTE, "B", 1),
        RoleResource(Roles.EXECUTE, "C", 1),
        //endregion
        //region user
        RoleResource(Roles.READ, "A", 2),
        RoleResource(Roles.EXECUTE, "A.B", 2),
        RoleResource(Roles.WRITE, "XY.UV.ABCDEFGHIJ", 2),
        RoleResource(Roles.READ, "A", 3),
        RoleResource(Roles.WRITE, "A.B", 3),
        RoleResource(Roles.EXECUTE, "A.B.C", 4),
        RoleResource(Roles.EXECUTE, "A.BC", 3),
        //endregion
    )

    private val users: List<User> = listOf(
        User("admin", "35d0239415e2371ee283a773f215c036", 1, "Salt"),  // 123
        User("user1", "b614d9bc7599d324e730dafbee318881", 2, "Salt1"), // qwerty123
        User("jdoe", "20718de57850c927f5127635a0a142bc", 3, "Salt2"),  // sup3rpaZZ
        User("jrow", "713c448ae8ff09d84d4037ca5d4934d9", 4, "Salt3"),  // Qweqrty12
        User("xxx", "50f1fa3d094cd33999010d52bcf348ee", 5, "Salt4")    // yyy
    )

    fun getRolesResources(): List<RoleResource> {
        return rolesResources
    }

    fun getUsers(): List<User> {
        return users
    }
}