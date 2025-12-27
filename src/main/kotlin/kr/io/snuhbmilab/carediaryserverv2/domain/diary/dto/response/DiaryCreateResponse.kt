
package kr.io.snuhbmilab.carediaryserverv2.domain.diary.dto.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "일기 생성 응답")
data class DiaryCreateResponse(
    @Schema(description = "AI가 생성한 일기 요약", example = "오늘 산책을 하며 좋은 기분을 느꼈군요! 활동적인 하루를 보내셨네요.")
    val summary: String
)