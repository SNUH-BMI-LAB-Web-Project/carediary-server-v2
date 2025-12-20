package kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.dto.response

import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.entity.ScaleQuestion

data class ScaleQuestionFindAllResponse(
    val scaleQuestions: List<ScaleQuestionDto>
) {
    data class ScaleQuestionDto(
        val scaleQuestionId: Long,
        val questionNumber: Int,
        val content: String
    ) {
        companion object {
            @JvmStatic
            fun from(scaleQuestion: ScaleQuestion) = ScaleQuestionDto(
                scaleQuestionId = scaleQuestion.id!!,
                questionNumber = scaleQuestion.questionNumber,
                content = scaleQuestion.content
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
