package kr.io.snuhbmilab.carediaryserverv2.domain.home.facade

import kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity.Diary
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.service.DiaryService
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.service.WelfareRecommendService
import kr.io.snuhbmilab.carediaryserverv2.domain.home.dto.response.HomeResponse
import kr.io.snuhbmilab.carediaryserverv2.domain.user.service.UserService
import org.apache.commons.lang3.concurrent.ConcurrentUtils.putIfAbsent
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Year
import java.time.YearMonth
import java.util.EnumMap
import java.util.UUID

@Service
@Transactional(readOnly = true)
class HomeFacade(
    private val userService: UserService,
    private val diaryService: DiaryService,
    private val welfareRecommendService: WelfareRecommendService
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

        val welfareServices = welfareRecommendService.findAllVisible(user)

        return HomeResponse.of(
            monthlyDiaryCount,
            yearlyDiaryCount,
            emotionCountMap,
            welfareServices
        )
    }
}