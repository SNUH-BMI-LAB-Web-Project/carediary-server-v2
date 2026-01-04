package kr.io.snuhbmilab.carediaryserverv2.domain.diary.repository

import kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity.Diary
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity.DiaryRecommendedQuestionUserScore
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface DiaryRecommendedQuestionUserScoreRepository : JpaRepository<DiaryRecommendedQuestionUserScore, Long> {
    fun findAllByDiaryOrderByIdAsc(diary: Diary): List<DiaryRecommendedQuestionUserScore>
    fun findAllByDiaryIdInOrderByIdAsc(diaryIds: List<UUID>): List<DiaryRecommendedQuestionUserScore>
}