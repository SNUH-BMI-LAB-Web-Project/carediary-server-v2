package kr.io.snuhbmilab.carediaryserverv2.domain.diary.facade

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.github.oshai.kotlinlogging.KotlinLogging
import kr.io.snuhbmilab.carediaryserverv2.common.exception.BusinessException
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.dto.request.DiaryCreateRequest
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.dto.response.DiaryCreateResponse
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.dto.response.DiaryDatesResponse
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.dto.response.DiaryDetailResponse
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.dto.response.DiaryFindAllResponse
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity.Diary
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.exception.DiaryErrorCode
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.service.DiaryRecommendedQuestionService
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.service.DiaryService
import kr.io.snuhbmilab.carediaryserverv2.domain.user.entity.User
import kr.io.snuhbmilab.carediaryserverv2.domain.user.service.UserService
import kr.io.snuhbmilab.carediaryserverv2.external.model.ModelClient
import kr.io.snuhbmilab.carediaryserverv2.external.model.ModelRestClient
import kr.io.snuhbmilab.carediaryserverv2.external.model.dto.GenerateSummaryRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.YearMonth
import java.util.UUID

private val logger = KotlinLogging.logger {}

@Service
@Transactional(readOnly = true)
class DiaryFacade(
    private val userService: UserService,
    private val diaryService: DiaryService,
    private val diaryRecommendedQuestionService: DiaryRecommendedQuestionService,
    private val modelClient: ModelClient
) {

    @Transactional
    fun createDiary(userId: UUID, request: DiaryCreateRequest): DiaryCreateResponse {
        val user = userService.findById(userId)

        //첫 작성이라면 사용자의 첫 작성일 저장
        if (diaryService.isFirstDiaryEntry(userId)) {
            userService.updateFirstDiaryDate(user, request.date)
        }

        val diary = diaryService.create(user, request.date, request.content, request.emotion)

        request.questionScores.forEach {
            diaryRecommendedQuestionService.appendQuestionUserScore(diary, it.questionText, it.score)
        }

        //외부 LLM 연동한 요약문 생성 API 호출
        val modelSummaryRequest = GenerateSummaryRequest.of(
            diary,
            request.questionScores.map { it.questionText to it.score }
        )
        val modelSummaryResponse = modelClient.generateSummary(modelSummaryRequest)
        val summary = modelSummaryResponse.body?.summary ?: "요약 없음"

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

    fun findDates(userId: UUID, month: YearMonth): DiaryDatesResponse {
        val user = userService.findById(userId)
        val dates = diaryService.findDatesMonthly(userId, month)

        return DiaryDatesResponse(dates)
    }

    private fun validateDiaryUploader(user: User, diary: Diary) {
        if (user.id != diary.uploader.id) {
            throw BusinessException(DiaryErrorCode.DIARY_ACCESS_DENIED)
        }
    }
}