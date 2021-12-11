package research_work.db

import java.sql.*

fun main() {
    val conn: Connection = connection()
}

fun connection(): Connection {
    val url = "jdbc:h2:tcp://localhost/~/test"
    val user = "sa"
    val passwd = ""

    try {
        return DriverManager.getConnection(url, user, passwd)
    } catch (ex: SQLException) {
        throw ex
    }
}