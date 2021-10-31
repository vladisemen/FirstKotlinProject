package project_auth.models

data class User(
    val login: String,
    val pass: String,
    val salt: String
)