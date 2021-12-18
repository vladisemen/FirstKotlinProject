import services.Authentication
import services.Authorization
import services.Parser
import services.flyway.Flyway
import services.log.Log
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val flyway = Flyway()
    val parser = Parser(args)
    val objectParams = parser.parser()

    flyway.startFirstMigration()
    val authentication = Authentication(objectParams)
    val authorization = Authorization(objectParams)

    val numberCode = authorization.authorization(
        authentication.authentication()
    )

    val log = Log(numberCode)
    log.log(objectParams)

    exitProcess(numberCode)
}


