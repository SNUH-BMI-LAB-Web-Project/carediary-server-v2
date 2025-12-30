package kr.io.snuhbmilab.carediaryserverv2.external.model.dto

import java.util.UUID

class GenerateSummaryResponse(
    val diaryId: UUID,
    val summary: String
) {
}