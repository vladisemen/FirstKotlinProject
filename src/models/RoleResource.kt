package models

data class RoleResource(
    val idUser: Int = 0,
    val resource: String,
    val role: Roles,
)