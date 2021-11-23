import services.AAA
import services.Authentication
import services.Authorization
import services.Parser
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val parser = Parser(args)
    val objectParams = parser.parser()

    val authentication = Authentication(objectParams)
    val authorization = Authorization(objectParams)

    exitProcess(numberCode)
}