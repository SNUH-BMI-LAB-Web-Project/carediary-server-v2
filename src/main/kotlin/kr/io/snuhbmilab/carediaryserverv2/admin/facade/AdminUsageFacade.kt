package kr.io.snuhbmilab.carediaryserverv2.admin.facade

import kr.io.snuhbmilab.carediaryserverv2.admin.dto.response.AdminUsageResponse
import kr.io.snuhbmilab.carediaryserverv2.admin.dto.response.AdminUserUsageResponse
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.service.DiaryAnalysisResultService
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.service.DiaryService
import kr.io.snuhbmilab.carediaryserverv2.domain.user.service.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.YearMonth

@Service
@Transactional(readOnly = true)
class AdminUsageFacade(
    private val userService: UserService,
    private val diaryService: DiaryService,
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

    fun getUserUsages(search: String?): AdminUserUsageResponse {
        val users = userService.searchByName(search)
        val userIds = users.mapNotNull { it.id }

        val diaryCountMap = diaryService.countByUserIds(userIds)
        val analysisCountMap = diaryAnalysisResultService.countByUserIds(userIds)

        val userUsages = users.map { user ->
            val userId = user.id!!

            AdminUserUsageResponse.UserUsageDto(
                userId = userId,
                userName = user.name,
                diaryCount = diaryCountMap[userId] ?: 0L,
                analysisCount = analysisCountMap[userId] ?: 0L
            )
        }

        return AdminUserUsageResponse.from(userUsages)
    }
}
