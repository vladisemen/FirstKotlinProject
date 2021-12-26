package dao

import Connection
import models.User
import java.sql.PreparedStatement

class AuthenticationEloquent(_login: String, _conn: Connection = Connection()) {

    private val сonn: Connection
    private val login: String

    init {
        сonn = _conn
        login = _login
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
        сonn.connection().let {
            val sql = "SELECT * FROM customer WHERE LOGIN = ?"

            val userData = it.prepareStatement(sql).let {
                it.setString(1, this.login)
                it.executeQuery()
            }

            while (userData.next()) {
                if (userData.getString("login") == this.login) {
                    val result =
                        User(userData.getString("login"), userData.getString("pass"), userData.getString("salt"))
                    it.close()

                    return result
                }
            }
        }
        return null
    }
}