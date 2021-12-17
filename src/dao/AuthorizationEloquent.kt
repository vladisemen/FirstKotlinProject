package dao

import Connection
import models.Roles
import java.sql.PreparedStatement

class AuthorizationEloquent(_resource: String = "", _role: Roles = Roles.READ) {

    private val сonn = Connection()
    private val resource: String
    private val role: Roles

    init {
        resource = _resource
        role = _role
    }

    /**
     * Имеет ли доступ к ресурсу
     */
    fun isCheckResourceAccess(login: String): Boolean {
        val st = сonn.connection()
        val sql = "SELECT c.login, r.role, rs.ress, rs.data_start, rs.data_end, rs.number FROM customer as c " +
                "INNER JOIN role as r ON r.login_customer = c.login " +
                "INNER JOIN resource as rs ON rs.id_role = r.id " +
                "WHERE c.login = ? " +
                "AND r.role = ?"

        val preparedStatement: PreparedStatement = st.prepareStatement(sql)
        preparedStatement.setString(1, login)
        preparedStatement.setString(2, role.toString())

        val userData = preparedStatement.executeQuery()

        while (userData.next()) {
            val lengthDateRes = userData.getString("ress").length

            if (resource.length >= lengthDateRes) {
                if (userData.getString("ress") == resource.substring(0, lengthDateRes)
                    && (lengthDateRes == resource.length || resource[lengthDateRes] == '.')
                ) {
                    return true
                }
            }
        }
        return false
    }
}