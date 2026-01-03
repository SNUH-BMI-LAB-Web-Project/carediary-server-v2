package kr.io.snuhbmilab.carediaryserverv2.domain.diary.dto.query

import java.util.UUID

interface UserCountQueryResult {
    val uploaderId: UUID
    val count: Long
}