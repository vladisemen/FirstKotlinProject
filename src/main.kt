import services.Authentication
import services.Authorization
import services.Parser
import services.log.Log
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val parser = Parser(args)
    val objectParams = parser.parser()

    val authentication = Authentication(objectParams)
    val authorization = Authorization(objectParams)

    val numberCode = authorization.authorization(
        authentication.authentication()
    )

    val log = Log(numberCode)
    log.log(objectParams)

    exitProcess(numberCode)
}


