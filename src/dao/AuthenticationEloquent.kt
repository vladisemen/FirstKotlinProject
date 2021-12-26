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
        val st = сonn.connection()
        val sql = "SELECT * FROM customer WHERE LOGIN = ?"
        val preparedStatement: PreparedStatement = st.prepareStatement(sql)

        preparedStatement.setString(1, this.login)
        val userData = preparedStatement.executeQuery()

        while (userData.next()) {
            if (userData.getString("login") == this.login) {
                val dataUser = User(userData.getString("login"), userData.getString("pass"), userData.getString("salt"))
                st.close()

                return dataUser
            }
        }
        st.close()
        return null
    }
}