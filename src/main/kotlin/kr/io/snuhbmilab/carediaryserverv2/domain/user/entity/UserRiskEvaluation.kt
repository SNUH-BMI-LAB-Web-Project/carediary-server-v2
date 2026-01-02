package kr.io.snuhbmilab.carediaryserverv2.domain.user.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.Lob
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import kr.io.snuhbmilab.carediaryserverv2.common.entity.BaseTimeEntity
import java.util.UUID

@Entity
@Table(name = "user_risk_evaluations")
class UserRiskEvaluation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ure_id", nullable = false)
    val id: Long? = null,

    @Column(name = "user_id", nullable = false)
    val userId: UUID,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    val user: User? = null,

    @Lob
    @Column(name = "risk_reason", nullable = false, columnDefinition = "TEXT")
    val riskReason: String,

    @Column(name = "is_at_risk", nullable = false, columnDefinition = "TINYINT(1)")
    val isAtRisk: Boolean = false,
) : BaseTimeEntity()