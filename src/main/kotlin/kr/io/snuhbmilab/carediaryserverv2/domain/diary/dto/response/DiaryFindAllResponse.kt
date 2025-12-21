package kr.io.snuhbmilab.carediaryserverv2.domain.diary.dto.response

import kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity.Diary
import java.time.LocalDate
import java.util.UUID

data class DiaryFindAllResponse(
    val diaries: List<DiaryDto>
) {
    data class DiaryDto(
        val diaryId: UUID,
        val date: LocalDate,
        val content: String,
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
