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
//    st.executeUpdate("CREATE TABLE customer(login VARCHAR(255) PRIMARY KEY, pass VARCHAR(255) not null)")
//    st.executeUpdate("CREATE TABLE role(id INT PRIMARY KEY, role VARCHAR(255) not null, login_customer VARCHAR(255) not null, FOREIGN KEY (login_customer) REFERENCES customer (login))")
//    st.executeUpdate("CREATE TABLE resource(id INT PRIMARY KEY, ress VARCHAR(255) not null,data_start VARCHAR(255),data_end VARCHAR(255), number INT, id_role INT not null, FOREIGN KEY (id_role) REFERENCES role (id))")
