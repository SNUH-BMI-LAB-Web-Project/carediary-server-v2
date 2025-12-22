package kr.io.snuhbmilab.carediaryserverv2.domain.diary.dto.query

import kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity.Diary

data class EmotionCountQueryResult(
    val emotion: Diary.Emotion,
    val count: Long
)
