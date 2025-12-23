package kr.io.snuhbmilab.carediaryserverv2.domain.user.service

import io.github.oshai.kotlinlogging.KotlinLogging
import kr.io.snuhbmilab.carediaryserverv2.domain.user.repository.UserRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

private val logger = KotlinLogging.logger {}

@Service
class UserTermCountScheduler(
    private val userRepository: UserRepository
) {

    @Transactional
    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    fun updateTermCount() {
        logger.info { "UserTermCountScheduler 시작" }

        val today = LocalDate.now()
        var successCount = 0
        var failCount = 0

        userRepository.findAllByFirstDiaryDateIsNotNullAndTermCountGreaterThan(0).forEach { user ->
            runCatching {
                val firstDiaryDate = user.firstDiaryDate!!
                val termCount = user.termCount

                if (firstDiaryDate.plusDays(7L * termCount) == today) {
                    user.termCount++
                    logger.info { "사용자 회기 증가: userId=${user.id!!} termCount=${termCount} -> ${user.termCount}" }
                    successCount++
                }
            }.onFailure { exception ->
                logger.error(exception) { "사용자 회기 업데이트 실패: userId=${user.id!!}" }
                failCount++
            }
        }

        logger.info { "UserTermCountScheduler 종료 [성공: $successCount / 실패: ${failCount}]" }
    }
}