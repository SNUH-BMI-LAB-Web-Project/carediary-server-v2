package kr.io.snuhbmilab.carediaryserverv2.admin.facade

import kr.io.snuhbmilab.carediaryserverv2.admin.dto.response.AdminUsageResponse
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.service.DiaryAnalysisResultService
import kr.io.snuhbmilab.carediaryserverv2.domain.user.service.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.YearMonth

@Service
@Transactional(readOnly = true)
class AdminUsageFacade(
    private val userService: UserService,
    private val diaryAnalysisResultService: DiaryAnalysisResultService
) {
    fun getUsage(): AdminUsageResponse {
        val monthStart = YearMonth.now().atDay(1).atStartOfDay()

        val cumulativeUserCount = userService.countAll()
        val cumulativeAnalysisCount = diaryAnalysisResultService.countAll()

        val monthlyUserCount = userService.countByCreatedAtAfter(monthStart)
        val monthlyAnalysisCount = diaryAnalysisResultService.countByCreatedAtAfter(monthStart)

        return AdminUsageResponse.of(
            cumulativeUserCount = cumulativeUserCount,
            cumulativeAnalysisCount = cumulativeAnalysisCount,
            monthlyUserCount = monthlyUserCount,
            monthlyAnalysisCount = monthlyAnalysisCount
        )
    }
}
