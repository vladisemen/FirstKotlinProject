package research_work.log

import org.apache.log4j.Logger

fun main() {
    val log = Log()
    log.log()
}

class Log() {
    private val log: Logger = Logger.getLogger(Log::class.java)

    fun log() {
        log.trace("Trace Message!");
        log.debug("Debug Message!");
        log.info("Info Message!");
        log.warn("Warn Message!");
        log.error("Error Message!");
        log.fatal("Fatal Message!");
    }
}