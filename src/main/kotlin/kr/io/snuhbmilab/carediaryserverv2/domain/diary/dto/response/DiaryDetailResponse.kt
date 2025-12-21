package kr.io.snuhbmilab.carediaryserverv2.domain.diary.dto.response

import kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity.Diary
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity.DiaryRecommendedQuestionUserScore
import java.time.LocalDate
import java.util.UUID

data class DiaryDetailResponse(
    val diaryId: UUID,
    val date: LocalDate,
    val content: String,
    val emotion: Diary.Emotion,
    val questionScores: List<RecommendedQuestionUserScoreDto>
) {
    data class RecommendedQuestionUserScoreDto(
        val questionText: String,
        val score: Int
    ) {
        companion object {
            @JvmStatic
            fun from(entity: DiaryRecommendedQuestionUserScore) = RecommendedQuestionUserScoreDto(
                questionText = entity.questionText,
                score = entity.score
            )
        }
    }

    companion object {
        @JvmStatic
        fun of(diary: Diary, questionScores: List<DiaryRecommendedQuestionUserScore>) = DiaryDetailResponse(
            diaryId = diary.id!!,
            date = diary.date,
            content = diary.content,
            emotion = diary.emotion,
            questionScores = questionScores.map { RecommendedQuestionUserScoreDto.from(it) }
        )
    }
}
