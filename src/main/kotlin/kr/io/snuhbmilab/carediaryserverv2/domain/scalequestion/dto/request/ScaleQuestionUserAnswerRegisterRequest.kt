package kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.dto.request

import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import org.hibernate.validator.constraints.Range

data class ScaleQuestionUserAnswerRegisterRequest(
    @field:NotEmpty(message = "응답 항목은 비어있을 수 없습니다")
    @field:Valid
    val items: List<ScaleQuestionUserAnswerItem>
) {
    data class ScaleQuestionUserAnswerItem(
        val scaleQuestionId: Long,

        @field:Range(min = 1, max = 5, message = "응답 값은 1 이상 5 이하여야 합니다")
        val answer: Int
    )
}
