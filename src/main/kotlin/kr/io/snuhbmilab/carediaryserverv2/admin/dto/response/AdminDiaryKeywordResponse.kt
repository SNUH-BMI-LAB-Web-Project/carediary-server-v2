package kr.io.snuhbmilab.carediaryserverv2.admin.dto.response

import kr.io.snuhbmilab.carediaryserverv2.common.utils.parseListFromDBText
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity.DiaryKeywordExtraction
import java.util.UUID

data class AdminDiaryKeywordResponse(
    val diaryId: UUID,
    val keywords: List<DiaryKeywordDto>
) {
    companion object {
        @JvmStatic
        fun of(diaryId: UUID, keywords: List<DiaryKeywordExtraction>) = AdminDiaryKeywordResponse(
            diaryId = diaryId,
            keywords = keywords.map { DiaryKeywordDto.from(it) }
        )
    }

    data class DiaryKeywordDto(
        val keywordExtractionId: Long,
        val keywordGroup: DiaryKeywordExtraction.KeywordGroup,
        val keyword: String,
        val keywordCode: String?,
        val evidences: List<String>
    ) {
        companion object {
            @JvmStatic
            fun from(entity: DiaryKeywordExtraction) = DiaryKeywordDto(
                keywordExtractionId = entity.id!!,
                keywordGroup = entity.keywordGroup,
                keyword = entity.keyword,
                keywordCode = entity.keywordCode,
                evidences = entity.evidences?.parseListFromDBText() ?: emptyList()
            )
        }
    }
}
