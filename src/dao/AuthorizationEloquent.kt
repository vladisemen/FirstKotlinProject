package dao

import services.Connection
import models.Roles

class AuthorizationEloquent(_resource: String = "", _role: Roles = Roles.READ, _conn: Connection = Connection()) {

    private val сonn: Connection
    private val resource: String
    private val role: Roles

    init {
        сonn = _conn
        resource = _resource
        role = _role
    }

    /**
     * Имеет ли доступ к ресурсу
     */
    fun isCheckResourceAccess(login: String): Boolean {
        сonn.connection().let {
            val sql = "SELECT c.login, r.role, rs.ress FROM customer as c " +
                    "INNER JOIN role as r ON r.login_customer = c.login " +
                    "INNER JOIN resource as rs ON rs.id_role = r.id " +
                    "WHERE c.login = ? " +
                    "AND r.role = ?"

            val userData = it.prepareStatement(sql).let {
                it.setString(1, login)
                it.setString(2, role.toString())

                it.executeQuery()
            }


            while (userData.next()) {
                val lengthDateRes = userData.getString("ress").length

                if (resource.length >= lengthDateRes) {
                    if (userData.getString("ress") == resource.substring(0, lengthDateRes)
                        && (lengthDateRes == resource.length || resource[lengthDateRes] == '.')
                    ) {
                        it.close()
                        return true
                    }
                }
            }
        }
        return false
    }
}