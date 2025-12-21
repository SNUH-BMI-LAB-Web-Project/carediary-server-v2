package kr.io.snuhbmilab.carediaryserverv2.domain.diary.dto.request

import kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity.Diary
import java.time.LocalDate

data class DiaryCreateRequest(
    val date: LocalDate,
    val content: String,
    val emotion: Diary.Emotion,
    val questionScores: List<QuestionScoreItem>
) {
    data class QuestionScoreItem(
        val questionText: String,
        val score: Int
    )
}
