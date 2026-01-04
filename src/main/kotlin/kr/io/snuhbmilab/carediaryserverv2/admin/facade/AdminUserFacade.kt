package kr.io.snuhbmilab.carediaryserverv2.admin.facade

import kr.io.snuhbmilab.carediaryserverv2.admin.dto.response.AdminUserDetailResponse
import kr.io.snuhbmilab.carediaryserverv2.admin.dto.response.AdminUserFindAllResponse
import kr.io.snuhbmilab.carediaryserverv2.admin.dto.response.AdminUserScaleFindAllResponse
import kr.io.snuhbmilab.carediaryserverv2.admin.dto.response.AdminUserScaleQuestionResultResponse
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.service.DiaryService
import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.service.ScaleQuestionService
import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.service.UserScaleService
import kr.io.snuhbmilab.carediaryserverv2.domain.user.service.UserRiskService
import kr.io.snuhbmilab.carediaryserverv2.domain.user.service.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Year
import java.time.YearMonth
import java.util.UUID

@Service
@Transactional(readOnly = true)
class AdminUserFacade(
    private val userService: UserService,
    private val diaryService: DiaryService,
    private val userScaleService: UserScaleService,
    private val scaleQuestionService: ScaleQuestionService,
    private val userRiskService: UserRiskService,
) {
    fun findUserById(userId: UUID): AdminUserDetailResponse {
        val user = userService.findById(userId)
        val diaryDates = diaryService.findDatesMonthly(userId, YearMonth.now())
        val monthlyDiaryCount = diaryService.countMonthly(userId, YearMonth.now())
        val yearlyDiaryCount = diaryService.countYearly(userId, Year.now())
        val emotionCountMap = diaryService.countByEmotion(userId)
        val riskReason = userRiskService.findCurrentRisk(userId)?.riskReason

        return AdminUserDetailResponse.of(user, diaryDates, monthlyDiaryCount, yearlyDiaryCount, emotionCountMap, riskReason)
    }

    fun findUserScales(userId: UUID): AdminUserScaleFindAllResponse {
        val user = userService.findById(userId)
        val userScales = userScaleService.findAll(user)

        return AdminUserScaleFindAllResponse.from(userScales)
    }

    fun findScaleQuestionResult(userId: UUID, count: Int): AdminUserScaleQuestionResultResponse {
        val user = userService.findById(userId)
        val userScales = userScaleService.findAllByTermCount(user, count)
        val userAnswerMap = scaleQuestionService.findUserAnswersByScaleCategory(userId, count)

        return AdminUserScaleQuestionResultResponse.of(user, userScales, userAnswerMap)
    }

    fun findAllUsers(): AdminUserFindAllResponse {
        val users = userService.findAllRegistered()
            .filterNot { it.isAdmin() }
        val riskEvaluations = userRiskService.findAllByUserIds(users.map { it.id!! })
            .groupBy { it.userId }

        val userEvaluations = users.map { user ->
            val latestEvaluation = riskEvaluations[user.id!!]?.maxByOrNull { it.createdAt }

            user to latestEvaluation
        }

        return AdminUserFindAllResponse.from(userEvaluations)
    }
}