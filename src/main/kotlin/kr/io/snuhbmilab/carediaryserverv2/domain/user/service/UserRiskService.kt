package kr.io.snuhbmilab.carediaryserverv2.domain.user.service

import kr.io.snuhbmilab.carediaryserverv2.domain.user.repository.UserRiskEvaluationRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserRiskService(
    private val userRiskEvaluationRepository: UserRiskEvaluationRepository
) {
    fun findAllByUserIds(userIds: List<UUID>) = userRiskEvaluationRepository.findAllByIsAtRiskIsTrueAndUserIdIn(userIds)

    fun findCurrentRisk(userId: UUID) = userRiskEvaluationRepository.findFirstByUserIdAndIsAtRiskIsTrueOrderByCreatedAtDesc(userId)
}