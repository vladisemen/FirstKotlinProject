package models

enum class ExitCodeEnum(val code: Int) {
    SUCCESS(0),
    HELP_OUTPUT(1),
    INVALID_LOGIN_FORMAT(2),
    INVALID_LOGIN(3),
    INVALID_PASSWORD(4),
    UNKNOWN_ROLE (5),
    NO_ACCESS(6),
    INCORRECT_ACTIVITY(7)
}