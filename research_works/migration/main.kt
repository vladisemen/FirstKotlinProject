package research_work.migration

import org.flywaydb.core.Flyway

fun main() {
    val flyway: Flyway = Flyway.configure()
        .load()

    flyway.migrate()
}
