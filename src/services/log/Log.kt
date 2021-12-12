package services.log

import models.Parser
import org.apache.log4j.Logger

class Log(_numberLog: Int) {

    private val numberLog: Int
    private val log: Logger = Logger.getLogger(Log::class.java)

    init {
        numberLog = _numberLog
    }

    fun log(objectParams: Parser) {
        when (this.numberLog) {
            0 -> log.info("Пользователь ${objectParams.login} аутентифицировался")
            1 -> log.error("Вывод справки")
            2 -> log.error("Неверный формат логина: ${objectParams.login}")
            3 -> log.error("Такого логина ${objectParams.login} не существует")
            4 -> log.error("Пароль ${objectParams.pass} для пользователя ${objectParams.login} неверный")
            5 -> log.error("Пользователь ${objectParams.login} ввел неизвестную роль ${objectParams.inputRole}")
            6 -> log.error("У пользователя ${objectParams.login} нет доступа к ресурсу ${objectParams.res}")
            7 -> log.error("Неправильный формат даты:${objectParams.de},${objectParams.ds} или значения 'vol':${objectParams.vol}")
        }
    }
}