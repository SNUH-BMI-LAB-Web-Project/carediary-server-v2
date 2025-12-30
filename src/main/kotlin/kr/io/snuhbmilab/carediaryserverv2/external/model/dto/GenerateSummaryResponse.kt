package kr.io.snuhbmilab.carediaryserverv2.external.model.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID

class GenerateSummaryResponse(
    @JsonProperty("diary_id")
    val diaryId: UUID,
    val summary: String
) {
}