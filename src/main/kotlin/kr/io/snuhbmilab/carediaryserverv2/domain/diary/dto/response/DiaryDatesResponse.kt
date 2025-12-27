package kr.io.snuhbmilab.carediaryserverv2.domain.diary.dto.response

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

@Schema(description = "월별 일기 작성 날짜 목록 응답")
data class DiaryDatesResponse(
    @Schema(description = "일기가 작성된 날짜 목록", example = "[\"2024-12-01\", \"2024-12-05\", \"2024-12-10\"]")
    val dates: List<LocalDate>
)