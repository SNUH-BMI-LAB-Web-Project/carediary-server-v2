package kr.io.snuhbmilab.carediaryserverv2.common.constants

enum class Role {
    USER, ADMIN;

    val authority: String = "ROLE_${name.uppercase()}"
}