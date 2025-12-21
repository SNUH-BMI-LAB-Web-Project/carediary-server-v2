package kr.io.snuhbmilab.carediaryserverv2.domain.diary.facade

import kr.io.snuhbmilab.carediaryserverv2.domain.diary.dto.request.DiaryCreateRequest
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.service.DiaryRecommendedQuestionService
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.service.DiaryService
import kr.io.snuhbmilab.carediaryserverv2.domain.user.service.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional(readOnly = true)
class DiaryFacade(
    private val userService: UserService,
    private val diaryService: DiaryService,
    private val diaryRecommendedQuestionService: DiaryRecommendedQuestionService
) {

    @Transactional
    fun createDiary(userId: UUID, request: DiaryCreateRequest) {
        val user = userService.findById(userId)

        //TODO: 외부 LLM 연동한 요약문 생성 API 호출

        val diary = diaryService.create(user, request.date, request.content, request.emotion)

        request.questionScores.forEach {
            diaryRecommendedQuestionService.appendQuestionUserScore(diary, it.questionId, it.score)
        }
    }
}