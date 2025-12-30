package kr.io.snuhbmilab.carediaryserverv2.external.model.dto

import com.fasterxml.jackson.annotation.JsonProperty
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity.Diary
import java.util.UUID

data class GenerateSummaryRequest(
    @JsonProperty("diary_id")
    val diaryId: UUID,
    val emotion: String,
    val content: String,
    @JsonProperty("recommended_questions")
    val recommendedQuestions: List<RecommendedQuestionItem>
) {
    companion object {
        fun of(diary: Diary, recommendedQuestions: List<Pair<String, Int>>): GenerateSummaryRequest {
            return GenerateSummaryRequest(
                diaryId = diary.id!!,
                emotion = diary.emotion.name,
                content = diary.content,
                recommendedQuestions = recommendedQuestions.map { RecommendedQuestionItem(it.first, it.second) }
            )
        }
    }

    data class RecommendedQuestionItem(
        val question: String,
        val score: Int
    )
}
