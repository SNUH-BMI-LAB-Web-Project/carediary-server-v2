
package kr.io.snuhbmilab.carediaryserverv2.domain.diary.dto.request

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.PastOrPresent
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity.Diary
import org.hibernate.validator.constraints.Range
import java.time.LocalDate

data class DiaryCreateRequest(
    @field:PastOrPresent(message = "날짜는 현재 또는 과거여야 합니다")
    val date: LocalDate,

    @field:NotBlank(message = "일기 내용은 비어있을 수 없습니다")
    val content: String,

    val emotion: Diary.Emotion,

    @field:NotEmpty(message = "질문 점수 항목은 비어있을 수 없습니다")
    @field:Valid
    val questionScores: List<QuestionScoreItem>
) {
    data class QuestionScoreItem(
        @field:NotBlank(message = "질문 텍스트는 비어있을 수 없습니다")
        val questionText: String,

        @field:Range(min = 0, max = 10, message = "점수는 0 이상 10 이하여야 합니다")
        val score: Int
    )
}