package dao

import DateBase
import models.Roles

class AuthorizationEloquent(_resource: String = "", _role: Roles = Roles.READ) {
    private val resource: String
    private val role: Roles

    init {
        resource = _resource
        role = _role
    }

    /**
     * Имеет ли доступ к ресурсу
     */
    fun isCheckResourceAccess(idUser: Int): Boolean {
        for (item in DateBase.getRolesResources(role, idUser, resource.count())) {
            val lengthDateRes = item.resource.length
            if (item.resource == resource.substring(0, lengthDateRes)
                && (lengthDateRes == resource.length || resource[lengthDateRes] == '.')
            ) {
                return true
            }
        }
        return false
    }
}