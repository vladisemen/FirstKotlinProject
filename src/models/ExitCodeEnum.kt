package models

enum class ExitCodeEnum(val text: String) {
    ZERO("успех"),
    FIRST("вывод справки"),
    SECOND("неверный формат логина"),
    THREE("неверный логин"),
    FOUR("неверный пароль"),
    FIVE("неизвестная роль"),
    SIX("нет доступа"),
    SEVEN("некорректная активность"),
}
