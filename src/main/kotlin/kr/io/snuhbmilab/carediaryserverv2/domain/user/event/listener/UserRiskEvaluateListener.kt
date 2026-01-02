package kr.io.snuhbmilab.carediaryserverv2.domain.user.event.listener

import io.github.oshai.kotlinlogging.KotlinLogging
import kr.io.snuhbmilab.carediaryserverv2.common.constants.ThreadPoolNames
import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.event.UserScaleQuestionResultRegisterEvent
import kr.io.snuhbmilab.carediaryserverv2.domain.user.service.UserRiskEvaluator
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

private val logger = KotlinLogging.logger {}

@Component
class UserRiskEvaluateListener(
    private val userRiskEvaluator: UserRiskEvaluator
) {
    @Async(ThreadPoolNames.USER_RISK_EVALUATION)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun onScaleQuestionResultRegistered(event: UserScaleQuestionResultRegisterEvent) {
        val userId = event.userId

        try {
            userRiskEvaluator.evaluate(userId)
        } catch(exception: Exception) {
            logger.error(exception) { "사용자 위험군 판별 실패 userId=$userId" }
        }
    }
}