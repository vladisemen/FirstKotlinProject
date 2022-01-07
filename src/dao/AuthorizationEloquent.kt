package dao

import models.Roles
import services.Connection
import java.lang.Exception

class AuthorizationEloquent(resource: String = "", role: Roles = Roles.READ) {

    private val сonn: Connection = Connection()
    private val resource: String
    private val role: Roles

    init {
        this.resource = resource
        this.role = role
    }

    /**
     * Имеет ли доступ к ресурсу
     */
    fun isCheckResourceAccess(login: String): Boolean {

        try {
            val sql = "SELECT c.login, r.role, rs.ress FROM customer as c " +
                    "INNER JOIN role as r ON r.login_customer = c.login " +
                    "INNER JOIN resource as rs ON rs.id_role = r.id " +
                    "WHERE c.login = ? " +
                    "AND r.role = ?"

            val userStatement = сonn.connection().prepareStatement(sql)

            userStatement.setString(1, login)
            userStatement.setString(2, role.toString())

            try {
                val userResult = userStatement.executeQuery()

                try {
                    while (userResult.next()) {
                        val lengthDateRes = userResult.getString("ress").length

                        if (resource.length >= lengthDateRes) {
                            if (userResult.getString("ress") == resource.substring(0, lengthDateRes)
                                && (lengthDateRes == resource.length || resource[lengthDateRes] == '.')
                            ) {
                                return true
                            }
                        }
                    }
                } catch (e: Exception) {
                    throw Exception("Error in working with the request")
                } finally {
                    userResult.close()
                }
            } catch (e: Exception) {
                throw Exception("Request for non-fulfillment")
            } finally {
                userStatement.close()
            }
        } catch (e: Exception) {
            throw Exception("No connection")
        } finally {
            сonn.connection().close()
        }
        return false
    }
}