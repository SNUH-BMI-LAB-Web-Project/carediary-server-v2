package kr.io.snuhbmilab.carediaryserverv2.domain.diary.dto.response

import io.swagger.v3.oas.annotations.media.Schema
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity.Diary
import java.time.LocalDate
import java.util.UUID

@Schema(description = "일기 목록 조회 응답")
data class DiaryFindAllResponse(
    @Schema(description = "일기 목록")
    val diaries: List<DiaryDto>
) {
    @Schema(description = "일기 요약 정보")
    data class DiaryDto(
        @Schema(description = "일기 ID", example = "550e8400-e29b-41d4-a716-446655440000")
        val diaryId: UUID,

        @Schema(description = "일기 작성 날짜", example = "2024-12-25")
        val date: LocalDate,

        @Schema(description = "일기 내용", example = "오늘은 날씨가 좋아서 산책을 했다.")
        val content: String,

        @Schema(description = "감정 상태", example = "HAPPY")
        val emotion: Diary.Emotion
    ) {
        companion object {
            @JvmStatic
            fun from(diary: Diary) =
                DiaryDto(
                    diaryId = diary.id!!,
                    date = diary.date,
                    content = diary.content,
                    emotion = diary.emotion
                )
        }
    }

    companion object {
        @JvmStatic
        fun from(diaries: List<Diary>) = DiaryFindAllResponse(diaries.map { DiaryDto.from(it) })
    }
}