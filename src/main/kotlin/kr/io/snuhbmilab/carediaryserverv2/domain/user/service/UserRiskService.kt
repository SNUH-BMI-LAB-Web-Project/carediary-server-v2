package kr.io.snuhbmilab.carediaryserverv2.domain.user.service

import kr.io.snuhbmilab.carediaryserverv2.domain.user.repository.UserRiskEvaluationRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserRiskService(
    private val userRickEvaluationRepository: UserRiskEvaluationRepository
) {
    fun findAllByUserIds(userIds: List<UUID>) = userRickEvaluationRepository.findAllByIsAtRiskIsTrueAndUserIdIn(userIds)

    fun findCurrentRisk(userId: UUID) = userRickEvaluationRepository.findFirstByUserIdAndIsAtRiskIsTrueOrderByCreatedAtDesc(userId)
}