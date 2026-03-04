package kr.io.snuhbmilab.carediaryserverv2.external.model.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID

data class GenerateWordCloudResponse(
    @JsonProperty("user_id")
    val userId: UUID,
    @JsonProperty("total_diaries")
    val totalDiaries: Int,
    @JsonProperty("total_tokens")
    val totalTokens: Int,
    val items: List<WordCloudItem>
) {
    data class WordCloudItem(
        val word: String,
        val count: Int
    )
}
