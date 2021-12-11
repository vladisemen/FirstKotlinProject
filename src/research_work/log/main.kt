package research_work.log

import org.apache.log4j.Level
import org.apache.log4j.Logger

fun main() {
    val log = Log()
    log.log()
}

class Log() {
    private val log: Logger = Logger.getLogger(Log::class.java)

    fun log() {
        log.info("Info Message!")

        log.log(Level.FATAL, "Logger Name :: " + Log::class.java + " :: Passed Message ::")
        log.log(Level.ERROR, "Logger Name :: " + Log::class.java + " :: Not Passed Message ::")
        log.getParent().log(Level.ERROR, "Root Logger :: Passed Message As Root Is Configured For ERROR Level messages")
    }
}