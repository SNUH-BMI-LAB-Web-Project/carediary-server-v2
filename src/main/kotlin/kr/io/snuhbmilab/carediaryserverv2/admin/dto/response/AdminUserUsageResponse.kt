package kr.io.snuhbmilab.carediaryserverv2.admin.dto.response

import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID

@Schema(description = "사용자별 사용량 조회 응답")
data class AdminUserUsageResponse(
    @Schema(description = "사용자별 사용량 목록")
    val users: List<UserUsageDto>
) {
    @Schema(description = "사용자별 사용량")
    data class UserUsageDto(
        @Schema(description = "사용자 ID")
        val userId: UUID,

        @Schema(description = "사용자 이름")
        val userName: String?,

        @Schema(description = "일기 작성 건수")
        val diaryCount: Long,

        @Schema(description = "분석 건수")
        val analysisCount: Long
    )

    companion object {
        fun from(userUsages: List<UserUsageDto>): AdminUserUsageResponse {
            return AdminUserUsageResponse(users = userUsages)
        }
    }
}