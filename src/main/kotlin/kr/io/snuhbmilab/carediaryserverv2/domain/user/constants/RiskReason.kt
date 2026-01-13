package kr.io.snuhbmilab.carediaryserverv2.domain.user.constants

enum class RiskReason(val description: String) {
    HIGH_PIE_SEVERITY("SDoH 검사 결과 심각도 이상 감지"),
    ANXIETY_DANGER("불안 위험"),
    ANXIETY_HIGH("불안이 높음"),
    DEPRESSION_DANGER("우울 위험"),
    DEPRESSION_HIGH("우울이 높음"),
    ANGER_DANGER("분노 위험");

    companion object {
        @JvmStatic
        fun composeDescription(reasons: List<RiskReason>) = reasons.joinToString(", ") { it.description }
    }
}