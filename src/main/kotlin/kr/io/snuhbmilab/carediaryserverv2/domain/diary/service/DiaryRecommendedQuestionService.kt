package kr.io.snuhbmilab.carediaryserverv2.domain.diary.service

import kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity.Diary
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity.DiaryRecommendedQuestionUserScore
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.repository.DiaryRecommendedQuestionUserScoreRepository
import org.springframework.stereotype.Service

@Service
class DiaryRecommendedQuestionService(
    private val diaryRecommendedQuestionUserScoreRepository: DiaryRecommendedQuestionUserScoreRepository
) {

    fun appendQuestionUserScore(diary: Diary, questionText: String, score: Int) =
        diaryRecommendedQuestionUserScoreRepository.save(
            DiaryRecommendedQuestionUserScore(
                diary = diary,
                questionText = questionText,
                score = score
            )
        )

    fun findAllByDiary(diary: Diary): List<DiaryRecommendedQuestionUserScore> =
        diaryRecommendedQuestionUserScoreRepository.findAllByDiaryOrderByIdAsc(diary)
}