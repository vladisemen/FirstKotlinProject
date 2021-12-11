package research_work.db

import java.sql.*

fun main() {
    val conn: Connection = connection()

    dml(conn);
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

fun dml(con: Connection) {
    val st = con.createStatement()
    st.executeUpdate("CREATE TABLE TEST(ID INT PRIMARY KEY, NAME VARCHAR(255))")
    st.executeUpdate("DROP TABLE TEST")
}