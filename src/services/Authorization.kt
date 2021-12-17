package services

import dao.AuthenticationEloquent
import dao.AuthorizationEloquent
import models.RoleResource
import models.Roles
import models.ExitCodeEnum
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import models.Parser

class Authorization(_parser: Parser) {

    private val parser: Parser

    init {
        parser = _parser
    }

    private val datePattern = "\\d{4}-\\d{2}-\\d{2}"

    fun authorization(codeAuth: Int): Int {
        if (codeAuth != 0) {
            return codeAuth
        }

        // проверка на наличие роли и ресурса, если их нет, то просто успешная аутентификация, тк вверху уже прошла
        if (parser.inputRole != "null" && parser.res != "null") {
            // проверка на существование роли
            Roles.values().find { it.name == parser.inputRole } ?: return ExitCodeEnum.UNKNOWN_ROLE.code

            val role = Roles.valueOf(parser.inputRole)

            // Данные авторизация
            val dataRoleResource = RoleResource(
                resource = parser.res.uppercase(),
                role = role,
            )

            val eloquentAuthentication = AuthenticationEloquent(parser.login)
            val loginUser = eloquentAuthentication.findUserByLogin()!!.login

            val eloquentAuthorization = AuthorizationEloquent(dataRoleResource.resource, dataRoleResource.role)

            // Авторизация
            if (eloquentAuthorization.isCheckResourceAccess(loginUser)) {
                if (parser.ds != "null" && parser.de != "null" && parser.vol != "null") {
                    if (this.isDateAndValueValid(parser.ds, parser.de, parser.vol)) {
                        return ExitCodeEnum.INCORRECT_ACTIVITY.code
                    }

                    val dateStart = LocalDate.parse(parser.ds, DateTimeFormatter.ISO_DATE)
                    val dateEnd = LocalDate.parse(parser.de, DateTimeFormatter.ISO_DATE)
                } else {
                    return ExitCodeEnum.SUCCESS.code// если не содержит дат, объема
                }
            } else {
                return ExitCodeEnum.NO_ACCESS.code
            }
        }
        return ExitCodeEnum.SUCCESS.code
    }

    private fun isDateAndValueValid(ds: String, de: String, value: String): Boolean {
        if (Regex(this.datePattern).matches(ds)
            && Regex(this.datePattern).matches(de)
            && Regex("\\d+").matches(value)
        ) {
            return false
        }
        return true
    }
}

