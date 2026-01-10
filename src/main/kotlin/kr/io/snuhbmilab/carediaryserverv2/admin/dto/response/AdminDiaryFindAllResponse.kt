package kr.io.snuhbmilab.carediaryserverv2.admin.dto.response

import io.swagger.v3.oas.annotations.media.Schema
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity.Diary
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity.DiaryRecommendedQuestionUserScore
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

@Schema(description = "관리자 일기 목록 조회 응답")
data class AdminDiaryFindAllResponse(
    @Schema(description = "일기 목록")
    val diaries: List<AdminDiaryDto>
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

    @Schema(description = "일기 정보")
    data class AdminDiaryDto(
        @Schema(description = "일기 ID", example = "550e8400-e29b-41d4-a716-446655440000")
        val diaryId: UUID,

        @Schema(description = "작성자 ID", example = "550e8400-e29b-41d4-a716-446655440001")
        val uploaderId: UUID,

        @Schema(description = "일기 작성 날짜", example = "2024-12-25")
        val date: LocalDate,

        @Schema(description = "일기 내용", example = "오늘은 날씨가 좋아서 산책을 했다.")
        val content: String,

        @Schema(description = "감정 상태", example = "HAPPY")
        val emotion: Diary.Emotion,

        @Schema(description = "생성 일시")
        val createdAt: LocalDateTime,

        @Schema(description = "추천 질문에 대한 사용자 점수 목록")
        val questionScores: List<RecommendedQuestionUserScoreDto>
    ) {
        companion object {
            @JvmStatic
            fun of(
                diary: Diary,
                questionScores: List<DiaryRecommendedQuestionUserScore>
            ) = AdminDiaryDto(
                diaryId = diary.id!!,
                uploaderId = diary.uploaderId,
                date = diary.date,
                content = diary.content,
                emotion = diary.emotion,
                createdAt = diary.createdAt,
                questionScores = questionScores.map { RecommendedQuestionUserScoreDto.from(it) }
            )
        }
    }

    companion object {
        @JvmStatic
        fun of(
            diaries: List<Diary>,
            questionScoresMap: Map<UUID, List<DiaryRecommendedQuestionUserScore>>
        ) = AdminDiaryFindAllResponse(
            diaries = diaries.map { diary ->
                AdminDiaryDto.of(diary, questionScoresMap[diary.id] ?: emptyList())
            }
        )
    }
}