package kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.event

import java.util.UUID

data class UserScaleQuestionResultRegisterEvent(
    val userId: UUID
)