package research_work.migration

import org.flywaydb.core.Flyway

fun main() {
    val flyway = Flyway.configure().dataSource("jdbc:h2:./src/research_work/migration/sql/test", "sa", "")
        .locations("filesystem:src/research_work/migration/db").load()

    flyway.migrate()
}