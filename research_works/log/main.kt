package research_work.log

import org.apache.log4j.Level
import org.apache.log4j.Logger

fun main() {
    val log = Log()
    log.log()
}

class Log() {
    private val log: Logger = Logger.getLogger(Log::class.java)

    fun log() {
        log.info("Info Message!")

        log.log(Level.FATAL, "Logger Name :: " + Log::class.java + " :: Passed Message ::")
        log.log(Level.ERROR, "Logger Name :: " + Log::class.java + " :: Not Passed Message ::")
        log.getParent().log(Level.ERROR, "Root Logger :: Passed Message As Root Is Configured For ERROR Level messages")
    }
}

//    val сonn = services.Connection()
//    val st = сonn.connection()

//    val sql = "INSERT INTO resource VALUES(6 ,'A.BCD','2000-11-11','2000-11-11',10,1)"
//    val preparedStatement: PreparedStatement = st.prepareStatement(sql)
//    val userData = preparedStatement.executeUpdate()

//    val sql11 = "DROP TABLE IF EXISTS resource;"
//    val preparedStatement11: PreparedStatement = st.prepareStatement(sql11)
//    val userData11 = preparedStatement11.executeUpdate()
//
//    val sql12 = "DROP TABLE IF EXISTS role;"
//    val preparedStatement12: PreparedStatement = st.prepareStatement(sql12)
//    val userData12 = preparedStatement12.executeUpdate()
//
//    val sql13 = "DROP TABLE IF EXISTS customer;"
//    val preparedStatement13: PreparedStatement = st.prepareStatement(sql13)
//    val userData13 = preparedStatement13.executeUpdate()
//
//    val sql = "CREATE TABLE customer(login VARCHAR(255) PRIMARY KEY, pass VARCHAR(255) not null, salt VARCHAR(255) not null)"
//    val preparedStatement: PreparedStatement = st.prepareStatement(sql)
//    val userData = preparedStatement.executeUpdate()
//
//    val sql1 = "CREATE TABLE role(id INT PRIMARY KEY, role VARCHAR(255) not null, login_customer VARCHAR(255) not null, FOREIGN KEY (login_customer) REFERENCES customer (login))"
//    val preparedStatement1: PreparedStatement = st.prepareStatement(sql1)
//    val userData1 = preparedStatement1.executeUpdate()
//
//    val sql2 = "CREATE TABLE resource(id INT PRIMARY KEY, ress VARCHAR(255) not null,data_start VARCHAR(255),data_end VARCHAR(255), number INT, id_role INT not null, FOREIGN KEY (id_role) REFERENCES role (id))"
//    val preparedStatement2: PreparedStatement = st.prepareStatement(sql2)
//    val userData2 = preparedStatement2.executeUpdate()
//
//        val sql4 = "INSERT INTO customer VALUES('admin', '35d0239415e2371ee283a773f215c036','Salt')"
//    val preparedStatement4: PreparedStatement = st.prepareStatement(sql4)
//    val userData4 = preparedStatement4.executeUpdate()



//    st.executeUpdate("CREATE TABLE customer(login VARCHAR(255) PRIMARY KEY, pass VARCHAR(255) not null)")
//    st.executeUpdate("CREATE TABLE role(id INT PRIMARY KEY, role VARCHAR(255) not null, login_customer VARCHAR(255) not null, FOREIGN KEY (login_customer) REFERENCES customer (login))")
//    st.executeUpdate("CREATE TABLE resource(id INT PRIMARY KEY, ress VARCHAR(255) not null,data_start VARCHAR(255),data_end VARCHAR(255), number INT, id_role INT not null, FOREIGN KEY (id_role) REFERENCES role (id))")

