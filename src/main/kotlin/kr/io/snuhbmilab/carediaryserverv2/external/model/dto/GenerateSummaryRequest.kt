package kr.io.snuhbmilab.carediaryserverv2.external.model.dto

import kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity.Diary
import java.util.UUID

data class GenerateSummaryRequest(
    val diaryId: UUID,
    val emotion: String,
    val content: String,
    val recommendedQuestions: List<RecommendedQuestionItem>
) {
    companion object {
        fun of(diary: Diary, recommendedQuestions: List<Pair<String, Int>>): GenerateSummaryRequest {
            return GenerateSummaryRequest(
                diaryId = diary.id!!,
                emotion = diary.emotion.name,
                content = diary.content,
                recommendedQuestions = recommendedQuestions.map { RecommendedQuestionItem.from(it) }
            )
        }
    }

    data class RecommendedQuestionItem(
        val question: String,
        val score: Int
    ) {
        companion object {
            @JvmStatic
            fun from(item: Pair<String, Int>) = RecommendedQuestionItem(item.first, item.second)
        }
    }
}
