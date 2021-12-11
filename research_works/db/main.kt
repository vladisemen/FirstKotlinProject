package research_work.db

import java.sql.*

fun main() {
    val conn: Connection = connection()

    ddl(conn)
    dml(conn)
}

fun connection(): Connection {
    val url = "jdbc:h2:~/test"
    val user = "sa"
    val passwd = ""

    try {
        return DriverManager.getConnection(url, user, passwd)
    } catch (ex: SQLException) {
        throw ex
    }
}

fun ddl(con: Connection) {
    val st = con.createStatement()
    st.executeUpdate("CREATE TABLE TEST(ID INT PRIMARY KEY, NAME VARCHAR(255))")
    st.executeUpdate("DROP TABLE TEST")
}

fun dml(con: Connection) {
    val st = con.createStatement()
    st.executeUpdate("CREATE TABLE TEST(ID INT PRIMARY KEY, NAME VARCHAR(255))")

    st.executeUpdate("INSERT INTO TEST VALUES(1, 'World')")
    st.executeUpdate("UPDATE TEST SET NAME='Hi' WHERE ID=1")
    val select = st.executeQuery("SELECT * FROM TEST")

    while (select.next()) {
        val id: Int = select.getInt("ID")
        val name: String = select.getString("NAME")

        print("ID: $id")
        println(", name: $name")
    }

    st.executeUpdate("DELETE FROM TEST WHERE ID=1")
    st.executeUpdate("DROP TABLE TEST")
}