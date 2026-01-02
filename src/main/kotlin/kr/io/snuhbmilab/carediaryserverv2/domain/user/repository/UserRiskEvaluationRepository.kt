package kr.io.snuhbmilab.carediaryserverv2.domain.user.repository

import kr.io.snuhbmilab.carediaryserverv2.domain.user.entity.UserRiskEvaluation
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserRiskEvaluationRepository : JpaRepository<UserRiskEvaluation, Long> {
    fun existsByUserIdAndRiskReasonAndIsAtRiskIsTrue(userId: UUID, riskReason: String): Boolean
    fun findAllByIsAtRiskIsTrueAndUserIdIn(userIds: List<UUID>): List<UserRiskEvaluation>
    fun findFirstByUserIdAndIsAtRiskIsTrueOrderByCreatedAtDesc(
        userId: UUID
    ): UserRiskEvaluation?
}