package project_auth.Models

data class RoleResource(
    val role: Roles?,
    val resource: String,
    val idUser: Int = 0,
)