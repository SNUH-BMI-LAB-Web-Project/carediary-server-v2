package kr.io.snuhbmilab.carediaryserverv2.common.utils

fun String.parseListFromDBText() = split("|")

fun List<String>.joinToStringDBText() = joinToString("|")