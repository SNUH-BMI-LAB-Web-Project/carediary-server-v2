package kr.io.snuhbmilab.carediaryserverv2.admin.dto.response

import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal

@Schema(description = "관리자 사용량 조회 응답")
data class AdminUsageResponse(
    @Schema(description = "누적 사용량")
    val cumulative: UsageDetail,

    @Schema(description = "월간 사용량")
    val monthly: UsageDetail
) {
    @Schema(description = "사용량 상세")
    data class UsageDetail(
        @Schema(description = "사용자 수", example = "150")
        val userCount: Long,

        @Schema(description = "분석 건 수", example = "1234")
        val analysisCount: Long,

        @Schema(description = "사용료 (원)", example = "50000.00")
        val usageFee: BigDecimal
    )

    companion object {
        fun of(
            cumulativeUserCount: Long,
            cumulativeAnalysisCount: Long,
            monthlyUserCount: Long,
            monthlyAnalysisCount: Long
        ): AdminUsageResponse {
            // TODO: 사용료 정책이 정해지면 실제 계산 로직으로 교체
            val dummyFeePerAnalysis = BigDecimal("100.00")

            return AdminUsageResponse(
                cumulative = UsageDetail(
                    userCount = cumulativeUserCount,
                    analysisCount = cumulativeAnalysisCount,
                    usageFee = dummyFeePerAnalysis.multiply(BigDecimal(cumulativeAnalysisCount))
                ),
                monthly = UsageDetail(
                    userCount = monthlyUserCount,
                    analysisCount = monthlyAnalysisCount,
                    usageFee = dummyFeePerAnalysis.multiply(BigDecimal(monthlyAnalysisCount))
                )
            )
        }
    }
}