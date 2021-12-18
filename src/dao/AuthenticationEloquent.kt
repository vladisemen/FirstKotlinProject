package dao

import Connection
import models.User
import java.sql.PreparedStatement

class AuthenticationEloquent(_login: String) {

    private val сonn = Connection()
    private val login: String

    init {
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
        val st = сonn.connection()
        val sql = "SELECT * FROM CUSTOMER WHERE LOGIN = ?"
        val preparedStatement: PreparedStatement = st.prepareStatement(sql)

        preparedStatement.setString(1, this.login)
        val userData = preparedStatement.executeQuery()

        while (userData.next()) {
            if (userData.getString("login") == this.login) {
                return User(userData.getString("login"), userData.getString("pass"), userData.getString("salt"))
            }
        }
        return null
    }
}