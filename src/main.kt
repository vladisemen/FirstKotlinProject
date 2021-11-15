import service.AAA
import service.Parser

fun main(args: Array<String>) {

    val parser = Parser(args)
    val objectParams = parser.parser()

    val isAAAService = AAA()
    val numberCode = isAAAService.funAAA(objectParams)

    isAAAService.exitCode(numberCode)
}
