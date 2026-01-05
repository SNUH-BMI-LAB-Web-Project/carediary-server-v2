package kr.io.snuhbmilab.carediaryserverv2.domain.home.facade

import kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity.Diary
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.service.DiaryService
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.service.WelfareRecommendService
import kr.io.snuhbmilab.carediaryserverv2.domain.home.dto.response.HomeResponse
import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.service.ScaleQuestionService
import kr.io.snuhbmilab.carediaryserverv2.domain.user.service.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Year
import java.time.YearMonth
import java.util.UUID

@Service
@Transactional(readOnly = true)
class HomeFacade(
    private val userService: UserService,
    private val diaryService: DiaryService,
    private val welfareRecommendService: WelfareRecommendService,
    private val scaleQuestionService: ScaleQuestionService
) {
    fun getHome(userId: UUID): HomeResponse {
        val user = userService.findById(userId)
        val monthlyDiaryCount = diaryService.countMonthly(userId, YearMonth.now())
        val yearlyDiaryCount = diaryService.countYearly(userId, Year.now())
        val emotionCountMap = diaryService.countByEmotion(userId)
            .toMutableMap()
            .apply {
                enumValues<Diary.Emotion>().forEach { putIfAbsent(it, 0) }
            }

        val welfareServices = welfareRecommendService.findTop3Visible(user)
        val termCount = user.termCount
        val isScaleQuestionRequired = scaleQuestionService.needsScaleQuestion(user, termCount)

        return HomeResponse.of(
            monthlyDiaryCount,
            yearlyDiaryCount,
            emotionCountMap,
            welfareServices,
            isScaleQuestionRequired,
            termCount
        )
    }
}