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
        userRepository.findAll().forEach { user ->
            if (user.firstDiaryDate == null) return

            val termCount = user.termCount

            if (termCount == 0) return

            val today = LocalDate.now()

            if (user.firstDiaryDate!!.plusDays(7L * termCount) == today) {
                user.termCount++
                logger.info { "사용자 회기 증가: userId=${user.id!!} termCount=${termCount} -> ${user.termCount}" }
            }
        }
        logger.info { "UserTermCountScheduler 종료" }
    }
}