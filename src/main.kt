import service.AAA
import service.Parser
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val parser = Parser(args)
    val objectParams = parser.parser()

    val isAAAService = AAA(objectParams)
    val numberCode = isAAAService.funAAA()

    exitProcess(numberCode)
}