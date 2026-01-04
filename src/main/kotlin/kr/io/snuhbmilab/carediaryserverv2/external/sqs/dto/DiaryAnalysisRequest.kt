package kr.io.snuhbmilab.carediaryserverv2.external.sqs.dto

import java.util.UUID

data class DiaryAnalysisRequest(
    val diaryId: UUID,
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
