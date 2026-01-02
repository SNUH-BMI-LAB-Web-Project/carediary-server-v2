package kr.io.snuhbmilab.carediaryserverv2.domain.user.constants

enum class RiskReason(val description: String) {
    HIGH_PIE_SEVERITY("SDoH 검사 결과 심각도 이상 감지"),
    HIGH_SCALE_SCORE("분노/불안/우울 척도 점수 이상 감지");

    companion object {
        @JvmStatic
        fun composeDescription(reasons: List<RiskReason>) = reasons.joinToString(", ") { it.description }
    }
}