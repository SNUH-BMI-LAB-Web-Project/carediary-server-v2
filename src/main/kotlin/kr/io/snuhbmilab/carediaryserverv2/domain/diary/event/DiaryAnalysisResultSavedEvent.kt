package kr.io.snuhbmilab.carediaryserverv2.domain.diary.event

import java.util.UUID

data class DiaryAnalysisResultSavedEvent(
    val diaryId: UUID,
    val uploaderId: UUID,
)
