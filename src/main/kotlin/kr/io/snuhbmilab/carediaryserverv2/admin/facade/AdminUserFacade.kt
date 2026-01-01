package kr.io.snuhbmilab.carediaryserverv2.admin.facade

import kr.io.snuhbmilab.carediaryserverv2.admin.dto.response.AdminUserDetailResponse
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.service.DiaryService
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
    private val diaryService: DiaryService
) {
    fun findUserById(userId: UUID): AdminUserDetailResponse {
        val user = userService.findById(userId)
        val diaryDates = diaryService.findDatesMonthly(userId, YearMonth.now())
        val monthlyDiaryCount = diaryService.countMonthly(userId, YearMonth.now())
        val yearlyDiaryCount = diaryService.countYearly(userId, Year.now())
        val emotionCountMap = diaryService.countByEmotion(userId)

        return AdminUserDetailResponse.of(user, diaryDates, monthlyDiaryCount, yearlyDiaryCount, emotionCountMap)
    }


}