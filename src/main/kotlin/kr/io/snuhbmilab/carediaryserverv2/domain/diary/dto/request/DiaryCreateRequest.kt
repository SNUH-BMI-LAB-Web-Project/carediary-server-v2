
package kr.io.snuhbmilab.carediaryserverv2.domain.diary.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.PastOrPresent
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity.Diary
import org.hibernate.validator.constraints.Range
import java.time.LocalDate

@Schema(description = "일기 생성 요청")
data class DiaryCreateRequest(
    @field:PastOrPresent(message = "날짜는 현재 또는 과거여야 합니다")
    @Schema(description = "일기 작성 날짜", example = "2024-12-25")
    val date: LocalDate,

    @field:NotBlank(message = "일기 내용은 비어있을 수 없습니다")
    @Schema(description = "일기 내용", example = "오늘은 날씨가 좋아서 산책을 했다. 기분이 많이 좋아졌다.")
    val content: String,

    @Schema(description = "감정 상태", example = "HAPPY")
    val emotion: Diary.Emotion,

    @field:NotEmpty(message = "질문 점수 항목은 비어있을 수 없습니다")
    @field:Valid
    @Schema(description = "추천 질문에 대한 사용자 점수 목록")
    val questionScores: List<QuestionScoreItem>
) {
    @Schema(description = "질문 점수 항목")
    data class QuestionScoreItem(
        @field:NotBlank(message = "질문 텍스트는 비어있을 수 없습니다")
        @Schema(description = "질문 텍스트", example = "오늘 하루 얼마나 활동적이었나요?")
        val questionText: String,

        @field:Range(min = 0, max = 10, message = "점수는 0 이상 10 이하여야 합니다")
        @Schema(description = "점수 (0-10)", example = "7", minimum = "0", maximum = "10")
        val score: Int
    )
}