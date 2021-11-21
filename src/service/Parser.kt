package service

import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import models.Parser

class Parser(_text: Array<String>) {

    private var text: Array<String> = _text

    fun parser(): Parser {
        // разбиение данных на параметры
        val parser = ArgParser("example")

        val login by parser.option(ArgType.String, shortName = "login", description = "Login")
        val pass by parser.option(ArgType.String, shortName = "pass", description = "Password")
        val res by parser.option(ArgType.String, shortName = "res", description = "Resource")
        val inputRole by parser.option(ArgType.String, shortName = "role", description = "Role")
        val ds by parser.option(ArgType.String, shortName = "ds", description = "Date start")
        val de by parser.option(ArgType.String, shortName = "de", description = "Date finish")
        val vol by parser.option(ArgType.String, shortName = "vol", description = "Number")

        parser.parse(this.text)

        return Parser(
            login.toString(),
            pass.toString(),
            res.toString(),
            inputRole.toString(),
            ds.toString(),
            de.toString(),
            vol.toString()
        )
    }
}

