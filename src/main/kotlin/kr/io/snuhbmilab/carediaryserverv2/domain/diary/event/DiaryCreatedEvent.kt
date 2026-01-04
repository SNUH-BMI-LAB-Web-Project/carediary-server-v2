package kr.io.snuhbmilab.carediaryserverv2.domain.diary.event

import java.util.UUID

data class DiaryCreatedEvent(
    val diaryId: UUID,
    val uploaderId: UUID
)
