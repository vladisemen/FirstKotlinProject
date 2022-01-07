package dao

import services.Connection
import models.User
import java.lang.Exception

class AuthenticationEloquent(login: String) {

    private val сonn: Connection = Connection()
    private val login: String

    init {
        this.login = login
    }

    /**
     * Есть ли такой логин в БД?
     */
    fun hasLogin(): Boolean {
        return findUserByLogin() != null
    }

    /**
     * Найдет и вернет юзера по логину
     */
    fun findUserByLogin(): User? {

        try {
            val sql = "SELECT * FROM customer WHERE LOGIN = ?"

            val userStatement = сonn.connection().prepareStatement(sql)
            userStatement.setString(1, this.login)

            try {
                val userResult = userStatement.executeQuery()

                try {
                    while (userResult.next()) {
                        if (userResult.getString("login") == this.login) {
                            return User(
                                userResult.getString("login"),
                                userResult.getString("pass"),
                                userResult.getString("salt")
                            )
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
        return null
    }
}