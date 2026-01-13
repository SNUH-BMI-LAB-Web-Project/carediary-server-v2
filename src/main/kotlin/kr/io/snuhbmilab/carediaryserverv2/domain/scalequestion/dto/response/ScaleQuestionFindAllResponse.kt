package kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.dto.response

import io.swagger.v3.oas.annotations.media.Schema
import kr.io.snuhbmilab.carediaryserverv2.common.utils.parseListFromDBText
import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.constants.ScaleCategory
import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.entity.ScaleQuestion

@Schema(description = "척도 질문 목록 조회 응답")
data class ScaleQuestionFindAllResponse(
    @Schema(description = "척도 질문 목록")
    val scaleQuestions: List<ScaleQuestionDto>
) {
    @Schema(description = "척도 질문 상세 정보")
    data class ScaleQuestionDto(
        @Schema(description = "척도 질문 ID", example = "1")
        val scaleQuestionId: Long,

        @Schema(description = "질문 번호", example = "1")
        val questionNumber: Int,

        @Schema(description = "질문 내용", example = "지난 일주일 동안 긴장되거나 불안했다")
        val content: String,

        @Schema(description = "척도 카테고리 (ANXIETY, DEPRESSION, ANGER)", example = "ANXIETY")
        val scaleCategory: ScaleCategory,

        @Schema(description = "선택지 개수", example = "5")
        val optionCount: Int,

        @Schema(description = "선택지 목록", example = "[\"전혀 아니다\", \"약간 그렇다\", \"보통이다\", \"많이 그렇다\", \"매우 그렇다\"]")
        val options: List<String>
    ) {
        companion object {
            @JvmStatic
            fun from(scaleQuestion: ScaleQuestion) = ScaleQuestionDto(
                scaleQuestionId = scaleQuestion.id!!,
                questionNumber = scaleQuestion.questionNumber,
                content = scaleQuestion.content,
                scaleCategory = scaleQuestion.scaleCategory,
                optionCount = scaleQuestion.optionCount,
                options = scaleQuestion.options.parseListFromDBText()
            )
        }
    }

    companion object {
        @JvmStatic
        fun from(scaleQuestions: List<ScaleQuestion>) = ScaleQuestionFindAllResponse(
            scaleQuestions = scaleQuestions.map { ScaleQuestionDto.from(it) }
        )
    }
}