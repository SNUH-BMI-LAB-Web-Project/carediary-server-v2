package kr.io.snuhbmilab.carediaryserverv2.domain.diary.dto.response

import java.time.LocalDate

data class DiaryDatesResponse(
    val dates: List<LocalDate>
) {
}