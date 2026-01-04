package kr.io.snuhbmilab.carediaryserverv2.domain.diary.service

import kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity.Diary
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity.DiaryRecommendedQuestionUserScore
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.repository.DiaryRecommendedQuestionUserScoreRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class DiaryRecommendedQuestionService(
    private val diaryRecommendedQuestionUserScoreRepository: DiaryRecommendedQuestionUserScoreRepository
) {

    fun appendQuestionUserScore(diary: Diary, questionText: String, score: Int): DiaryRecommendedQuestionUserScore =
        diaryRecommendedQuestionUserScoreRepository.save(
            DiaryRecommendedQuestionUserScore(
                diary = diary,
                questionText = questionText,
                score = score
            )
        )

    fun findAll(diary: Diary): List<DiaryRecommendedQuestionUserScore> =
        diaryRecommendedQuestionUserScoreRepository.findAllByDiaryOrderByIdAsc(diary)

    fun findAllMapByDiaryIds(diaryIds: List<UUID>): Map<UUID, List<DiaryRecommendedQuestionUserScore>> {
        if (diaryIds.isEmpty()) return emptyMap()
        return diaryRecommendedQuestionUserScoreRepository
            .findAllByDiaryIdInOrderByIdAsc(diaryIds)
            .groupBy { it.diary.id!! }
    }
}