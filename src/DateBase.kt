import models.RoleResource
import models.Roles
import models.User

object DateBase {
    fun getRolesResources(role: Roles, idUser: Int, countSumbols: Int): List<RoleResource> {
        val listRoleRes = listOf(
            //region admin
            RoleResource(1, "A", Roles.READ),
            RoleResource(1, "B", Roles.READ),
            RoleResource(1, "C", Roles.READ),
            RoleResource(1, "A", Roles.WRITE),
            RoleResource(1, "B", Roles.WRITE),
            RoleResource(1, "C", Roles.WRITE),
            RoleResource(1, "A", Roles.EXECUTE),
            RoleResource(1, "B", Roles.EXECUTE),
            RoleResource(1, "C", Roles.EXECUTE),
            //endregion
            //region user
            RoleResource(2, "A", Roles.READ),
            RoleResource(2, "A.B", Roles.EXECUTE),
            RoleResource(2, "XY.UV.ABCDEFGHIJ", Roles.WRITE),
            RoleResource(3, "A", Roles.READ),
            RoleResource(3, "A.B", Roles.WRITE),
            RoleResource(4, "A.B.C", Roles.EXECUTE),
            RoleResource(3, "A.BC", Roles.EXECUTE),
            RoleResource(6, "A.B", Roles.READ)
            //endregion
        )
        return listRoleRes.filter { it.idUser == idUser && it.role == role && it.resource.length <= countSumbols }
    }

    fun getUsers(): List<User> = listOf(
        User("admin", "35d0239415e2371ee283a773f215c036", 1, "Salt"),  // 123
        User("user1", "b614d9bc7599d324e730dafbee318881", 2, "Salt1"), // qwerty123
        User("jdoe", "20718de57850c927f5127635a0a142bc", 3, "Salt2"),  // sup3rpaZZ
        User("jrow", "713c448ae8ff09d84d4037ca5d4934d9", 4, "Salt3"),  // Qweqrty12
        User("xxx", "50f1fa3d094cd33999010d52bcf348ee", 5, "Salt4"),    // yyy
        User("null", "35d0239415e2371ee283a773f215c036", 6, "Salt")    // 123
    )
}