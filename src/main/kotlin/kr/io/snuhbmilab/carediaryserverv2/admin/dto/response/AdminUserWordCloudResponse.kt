package kr.io.snuhbmilab.carediaryserverv2.admin.dto.response

import io.swagger.v3.oas.annotations.media.Schema
import kr.io.snuhbmilab.carediaryserverv2.external.model.dto.GenerateWordCloudResponse
import java.util.UUID

@Schema(description = "관리자 사용자 워드클라우드 조회 결과")
data class AdminUserWordCloudResponse(
    @Schema(description = "사용자 ID", example = "550e8400-e29b-41d4-a716-446655440000")
    val userId: UUID,

    @Schema(description = "분석에 사용된 일기 개수", example = "30")
    val totalDiaries: Int,

    @Schema(description = "전체 단어 총합", example = "2150")
    val totalTokens: Int,

    @Schema(description = "상위 단어 목록")
    val items: List<WordCloudItemDto>
) {
    companion object {
        fun from(response: GenerateWordCloudResponse): AdminUserWordCloudResponse {
            return AdminUserWordCloudResponse(
                userId = response.userId,
                totalDiaries = response.totalDiaries,
                totalTokens = response.totalTokens,
                items = response.items.map { WordCloudItemDto(it.word, it.count) }
            )
        }
    }

    @Schema(description = "워드클라우드 단어 항목")
    data class WordCloudItemDto(
        @Schema(description = "단어", example = "병원")
        val word: String,

        @Schema(description = "등장 횟수", example = "32")
        val count: Int
    )
}
