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
            /**
             * Creates a ScaleQuestionDto from a ScaleQuestion entity.
             *
             * @param scaleQuestion The source entity to convert; its `id` must be non-null.
             * @return A ScaleQuestionDto containing the entity's `id`, `questionNumber`, and `content`.
             */
            @JvmStatic
            fun from(scaleQuestion: ScaleQuestion) = ScaleQuestionDto(
                scaleQuestionId = scaleQuestion.id!!,
                questionNumber = scaleQuestion.questionNumber,
                content = scaleQuestion.content
            )
        }
    }

    companion object {
        /**
         * Create a ScaleQuestionFindAllResponse from a list of ScaleQuestion entities.
         *
         * @param scaleQuestions The list of ScaleQuestion entities to convert to DTOs.
         * @return A ScaleQuestionFindAllResponse containing the converted ScaleQuestionDto list.
         */
        @JvmStatic
        fun from(scaleQuestions: List<ScaleQuestion>) = ScaleQuestionFindAllResponse(
            scaleQuestions = scaleQuestions.map { ScaleQuestionDto.from(it) }
        )
    }
}