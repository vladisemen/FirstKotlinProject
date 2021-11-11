package models

data class User(
    val login: String,
    val pass: String,
    val id: Int = 0,
    val salt: String = ""
)