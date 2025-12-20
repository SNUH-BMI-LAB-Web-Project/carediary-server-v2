package kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.dto.request

data class ScaleQuestionUserAnswerRegisterRequest(
    val items: List<ScaleQuestionUserAnswerItem>
) {
    data class ScaleQuestionUserAnswerItem(
        val scaleQuestionId: Long,
        val answer: Int
    )
}
