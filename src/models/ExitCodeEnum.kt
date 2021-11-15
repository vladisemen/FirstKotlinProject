package models

enum class ExitCodeEnum {
    ZERO {
        override fun printExitCode(): Int {
            println("успех")
            return 0
        }
    },
    FIRST { // Парсер не дает выводить
        override fun printExitCode(): Int {
            println("вывод справки")
            return 1
        }
    },
    SECOND {
        override fun printExitCode(): Int {
            println("неверный формат логина")
            return 2
        }
    },
    THREE {
        override fun printExitCode(): Int {
            println("неверный пароль")
            return 3
        }
    },
    FOUR {
        override fun printExitCode(): Int {
            println("неизвестная роль")
            return 4
        }
    },
    FIVE {
        override fun printExitCode(): Int {
            println("нет доступа")
            return 5
        }
    },
    SIX {
        override fun printExitCode(): Int {
            println("вывод справки")
            return 6
        }
    },
    SEVEN {
        override fun printExitCode(): Int {
            println("некорректная активность")
            return 7
        }
    };

    abstract fun printExitCode(): Int
}
