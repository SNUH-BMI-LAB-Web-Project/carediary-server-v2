package kr.io.snuhbmilab.carediaryserverv2.domain.user.event.listener

import kr.io.snuhbmilab.carediaryserverv2.common.constants.ThreadPoolNames
import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.event.UserScaleQuestionResultRegisterEvent
import kr.io.snuhbmilab.carediaryserverv2.domain.user.service.UserRiskEvaluator
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class UserRiskEvaluateListener(
    private val userRiskEvaluator: UserRiskEvaluator
) {
    @Async(ThreadPoolNames.USER_RISK_EVALUATION)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun onScaleQuestionResultRegistered(event: UserScaleQuestionResultRegisterEvent) {
        val userId = event.userId

        userRiskEvaluator.evaluate(userId)
    }
}