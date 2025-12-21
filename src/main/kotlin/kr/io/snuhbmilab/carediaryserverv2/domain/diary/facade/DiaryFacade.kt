package kr.io.snuhbmilab.carediaryserverv2.domain.diary.facade

import kr.io.snuhbmilab.carediaryserverv2.common.exception.BusinessException
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.dto.request.DiaryCreateRequest
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.dto.response.DiaryCreateResponse
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.dto.response.DiaryDetailResponse
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.dto.response.DiaryFindAllResponse
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity.Diary
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.exception.DiaryErrorCode
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.service.DiaryRecommendedQuestionService
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.service.DiaryService
import kr.io.snuhbmilab.carediaryserverv2.domain.user.entity.User
import kr.io.snuhbmilab.carediaryserverv2.domain.user.service.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.UUID

@Service
@Transactional(readOnly = true)
class DiaryFacade(
    private val userService: UserService,
    private val diaryService: DiaryService,
    private val diaryRecommendedQuestionService: DiaryRecommendedQuestionService
) {

    @Transactional
    fun createDiary(userId: UUID, request: DiaryCreateRequest): DiaryCreateResponse {
        val user = userService.findById(userId)

        val diary = diaryService.create(user, request.date, request.content, request.emotion)

        request.questionScores.forEach {
            diaryRecommendedQuestionService.appendQuestionUserScore(diary, it.questionText, it.score)
        }

        //TODO: 외부 LLM 연동한 요약문 생성 API 호출
        val summary = "고생했어요~ diaryId: ${diary.id}, diaryDate: ${diary.date}"

        return DiaryCreateResponse(summary)
    }

    fun findAllDiariesByMe(userId: UUID, startDate: LocalDate?, endDate: LocalDate?): DiaryFindAllResponse {
        val user = userService.findById(userId)
        val diaries = diaryService.findAllByUserAndPeriod(user, startDate, endDate)

        return DiaryFindAllResponse.from(diaries)
    }

    fun findDiaryById(userId: UUID, diaryId: UUID): DiaryDetailResponse {
        val user = userService.findById(userId)
        val diary = diaryService.findById(diaryId)

        validateDiaryUploader(user, diary)

        val questionScores = diaryRecommendedQuestionService.findAllByDiary(diary)

        return DiaryDetailResponse.of(diary, questionScores)
    }

    private fun validateDiaryUploader(user: User, diary: Diary) {
        if (user.id != diary.uploader.id) {
            throw BusinessException(DiaryErrorCode.DIARY_ACCESS_DENIED)
        }
    }
}