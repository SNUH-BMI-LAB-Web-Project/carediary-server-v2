package kr.io.snuhbmilab.carediaryserverv2.admin.dto.response

import io.swagger.v3.oas.annotations.media.Schema
import kr.io.snuhbmilab.carediaryserverv2.common.utils.parseListFromDBText
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity.DiaryKeywordExtraction
import java.util.UUID

@Schema(description = "관리자 일기 키워드 조회 응답")
data class AdminDiaryKeywordResponse(
    @Schema(description = "일기 ID", example = "550e8400-e29b-41d4-a716-446655440000")
    val diaryId: UUID,

    @Schema(description = "추출된 키워드 목록")
    val keywords: List<DiaryKeywordDto>
) {
    companion object {
        @JvmStatic
        fun of(diaryId: UUID, keywords: List<DiaryKeywordExtraction>) = AdminDiaryKeywordResponse(
            diaryId = diaryId,
            keywords = keywords.map { DiaryKeywordDto.from(it) }
        )
    }

    @Schema(description = "일기 키워드 정보")
    data class DiaryKeywordDto(
        @Schema(description = "키워드 추출 ID", example = "1")
        val keywordExtractionId: Long,

        @Schema(description = "키워드 그룹 (LIFE_CYCLE: 생애주기, HOUSEHOLD_STATUS: 가구상황, INTERESTS: 관심사)", example = "LIFE_CYCLE")
        val keywordGroup: DiaryKeywordExtraction.KeywordGroup,

        @Schema(description = "키워드", example = "청년")
        val keyword: String,

        @Schema(description = "키워드 코드", example = "LC001")
        val keywordCode: String?,

        @Schema(description = "키워드 추출 근거 문장 목록", example = "[\"취업 준비를 하고 있다\", \"청년 주거 지원이 필요하다\"]")
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