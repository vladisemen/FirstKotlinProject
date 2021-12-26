package services.flyway

import org.flywaydb.core.Flyway
import java.io.File

class Flyway() {

    private val url = "jdbc:h2:./src/services/flyway/sql/db"
    private val user = "sa"
    private val password = ""
    private val pathFileMigrations = "filesystem:./src/services/flyway/migrations"

    fun startFirstMigration() {

        if (!File("./src/services/flyway/sql/db.mv.db").exists()) {
            val flyway = Flyway.configure().dataSource(url, user, password)
                .locations(pathFileMigrations).load()

            flyway.migrate()
        }
    }
}