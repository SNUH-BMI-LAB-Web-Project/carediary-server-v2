package kr.io.snuhbmilab.carediaryserverv2.domain.diary.dto.response

import io.swagger.v3.oas.annotations.media.Schema
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity.Diary
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity.DiaryRecommendedQuestionUserScore
import java.time.LocalDate
import java.util.UUID

@Schema(description = "일기 상세 조회 응답")
data class DiaryDetailResponse(
    @Schema(description = "일기 ID", example = "550e8400-e29b-41d4-a716-446655440000")
    val diaryId: UUID,

    @Schema(description = "일기 작성 날짜", example = "2024-12-25")
    val date: LocalDate,

    @Schema(description = "일기 내용", example = "오늘은 날씨가 좋아서 산책을 했다.")
    val content: String,

    @Schema(description = "감정 상태", example = "HAPPY")
    val emotion: Diary.Emotion,

    @Schema(description = "추천 질문에 대한 사용자 점수 목록")
    val questionScores: List<RecommendedQuestionUserScoreDto>
) {
    @Schema(description = "추천 질문 점수")
    data class RecommendedQuestionUserScoreDto(
        @Schema(description = "질문 텍스트", example = "오늘 하루 얼마나 활동적이었나요?")
        val questionText: String,

        @Schema(description = "사용자가 입력한 점수", example = "7")
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