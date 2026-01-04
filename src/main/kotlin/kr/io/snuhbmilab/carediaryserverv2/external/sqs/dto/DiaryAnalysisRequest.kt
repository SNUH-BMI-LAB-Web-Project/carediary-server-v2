package kr.io.snuhbmilab.carediaryserverv2.external.sqs.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID

data class DiaryAnalysisRequest(
    @JsonProperty("diary_id")
    val diaryId: UUID,
    @JsonProperty("uploader_id")
    val uploaderId: UUID
) {
    companion object {
        @JvmStatic
        fun of(diaryId: UUID, uploaderId: UUID): DiaryAnalysisRequest {
            return DiaryAnalysisRequest(
                diaryId = diaryId,
                uploaderId = uploaderId
            )
        }
    }
}
