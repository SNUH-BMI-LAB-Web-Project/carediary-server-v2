package kr.io.snuhbmilab.carediaryserverv2.external.model.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID

data class GenerateWordCloudRequest(
    @JsonProperty("user_id")
    val userId: UUID,
    @JsonProperty("top_k")
    val topK: Int,
    @JsonProperty("max_diaries")
    val maxDiaries: Int?
)
