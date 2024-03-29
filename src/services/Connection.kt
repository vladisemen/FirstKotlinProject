package services

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class Connection {

    private val url = "jdbc:h2:./src/sql/db"
    private val user = "sa"
    private val passwd = ""

    fun connection(): Connection {
        try {
            return DriverManager.getConnection(url, user, passwd)
        } catch (ex: SQLException) {
            throw ex
        }
    }
}