package kr.io.snuhbmilab.carediaryserverv2.common.utils

fun String.parseListFromDBText(): List<String> {
    if (isBlank()) return emptyList()
    return split("|")
        .map { it.trim() }
        .filter { it.isNotEmpty() }
}

fun List<String>.joinToStringDBText(): String = joinToString("|")