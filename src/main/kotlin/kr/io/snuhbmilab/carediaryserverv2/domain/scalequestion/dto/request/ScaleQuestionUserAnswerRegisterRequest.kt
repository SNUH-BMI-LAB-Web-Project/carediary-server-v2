package kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import org.hibernate.validator.constraints.Range

@Schema(description = "척도 질문 응답 등록 요청")
data class ScaleQuestionUserAnswerRegisterRequest(
    @field:NotEmpty(message = "응답 항목은 비어있을 수 없습니다")
    @field:Valid
    @Schema(description = "척도 질문 응답 항목 목록")
    val items: List<ScaleQuestionUserAnswerItem>
) {
    @Schema(description = "척도 질문 응답 항목")
    data class ScaleQuestionUserAnswerItem(
        @Schema(description = "척도 질문 ID", example = "1")
        val scaleQuestionId: Long,

        @field:Range(min = 1, max = 5, message = "응답 값은 1 이상 5 이하여야 합니다")
        @Schema(description = "응답 값 (1-5)", example = "3", minimum = "1", maximum = "5")
        val answer: Int
    )
}