package project_auth.models

data class RoleResource(
    val role: Roles,
    val resource: String,
    val idUser: Int,
)