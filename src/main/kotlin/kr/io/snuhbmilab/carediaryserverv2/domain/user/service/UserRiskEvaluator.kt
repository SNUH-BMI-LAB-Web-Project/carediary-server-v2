package kr.io.snuhbmilab.carediaryserverv2.domain.user.service

import io.github.oshai.kotlinlogging.KotlinLogging
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.repository.PieRepository
import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.repository.UserScaleRepository
import kr.io.snuhbmilab.carediaryserverv2.domain.user.constants.RiskReason
import kr.io.snuhbmilab.carediaryserverv2.domain.user.entity.UserRiskEvaluation
import kr.io.snuhbmilab.carediaryserverv2.domain.user.repository.UserRiskEvaluationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

private val logger = KotlinLogging.logger {}

@Service
class UserRiskEvaluator(
    private val pieRepository: PieRepository,
    private val userScaleRepository: UserScaleRepository,
    private val userRiskEvaluationRepository: UserRiskEvaluationRepository,
    private val userService: UserService
) {
    companion object {
        private const val SEVERITY_THRESHOLD = 4
        private const val SCORE_THRESHOLD = 20
    }

    @Transactional
    fun evaluate(userId: UUID) {
        logger.info { "위험군 판별 시작 userId=$userId" }
        val user = userService.findById(userId)
        val riskReasons = mutableListOf<RiskReason>()

        // 1. Pie 심각도 체크 (severity >= 4)
        val hasHighSeverityPie = pieRepository.existsByDiaryUploaderIdAndSeverityGreaterThanEqual(userId, SEVERITY_THRESHOLD)
        if (hasHighSeverityPie) {
            riskReasons.add(RiskReason.HIGH_PIE_SEVERITY)
        }

        // 2. UserScale 점수 체크 (score >= 20, 현재 termCount 기준)
        val currentTermCount = user.termCount
        val hasHighScaleScore = userScaleRepository.existsByUserIdAndTermCountAndScoreGreaterThanEqual(userId, currentTermCount, SCORE_THRESHOLD)
        if (hasHighScaleScore) {
            riskReasons.add(RiskReason.HIGH_SCALE_SCORE)
        }

        // 3. 위험군 여부 및 사유 결정
        val isAtRisk = riskReasons.isNotEmpty()
        val description = RiskReason.composeDescription(riskReasons)

        // 4. UserRiskEvaluation 저장
        if (isAtRisk) {
            if (userRiskEvaluationRepository.existsByUserIdAndRiskReasonAndIsAtRiskIsTrue(userId, description)) {
                logger.info { "동일 내용의 위험군 판별 기록이 이미 존재합니다. userId=$userId, description=$description" }
                return
            }

            val evaluation = UserRiskEvaluation(
                userId = userId,
                riskReason = description,
                isAtRisk = true
            )
            userRiskEvaluationRepository.save(evaluation)
            logger.info { "위험군 판별 추가 userId=$userId, description=$description" }
        }
        logger.info { "위험군 판별 종료 userId=$userId" }
    }
}