package kr.io.snuhbmilab.carediaryserverv2.common.utils

import java.time.LocalDate
import java.time.Year
import java.time.YearMonth

fun YearMonth.toDateRange(): ClosedRange<LocalDate> =
    atDay(1)..atEndOfMonth()

fun Year.toDateRange(): ClosedRange<LocalDate> =
    atDay(1)..atDay(length())