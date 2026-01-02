package kr.io.snuhbmilab.carediaryserverv2.admin.dto.response

import io.swagger.v3.oas.annotations.media.Schema
import kr.io.snuhbmilab.carediaryserverv2.domain.user.entity.User
import kr.io.snuhbmilab.carediaryserverv2.domain.user.entity.UserRiskEvaluation
import java.time.LocalDate
import java.util.UUID

@Schema(description = "관리자 전체 사용자 목록 조회 응답")
data class AdminUserFindAllResponse(
    @Schema(description = "사용자 목록")
    val users: List<AdminUserDto>
) {
    companion object {
        @JvmStatic
        fun from(users: List<Pair<User, UserRiskEvaluation?>>) = AdminUserFindAllResponse(
            users = users.map { AdminUserDto.from(it.first, it.second) }
        )
    }

    @Schema(description = "관리자 사용자 정보")
    data class AdminUserDto(
        @Schema(description = "사용자 ID", example = "550e8400-e29b-41d4-a716-446655440000")
        val userId: UUID,

        @Schema(description = "이름", example = "홍길동")
        val name: String,

        @Schema(description = "생년월일", example = "1990-01-15")
        val birthDate: LocalDate,

        @Schema(description = "주 진단명", example = "조현병")
        val primaryDiagnosis: String?,

        @Schema(description = "위험군 여부", example = "false")
        val isAtRisk: Boolean
    ) {
        companion object {
            @JvmStatic
            fun from(user: User, userRiskEvaluation: UserRiskEvaluation?) = AdminUserDto(
                userId = user.id!!,
                name = user.name!!,
                birthDate = user.birthDate!!,
                primaryDiagnosis = user.primaryDiagnosis,
                isAtRisk = userRiskEvaluation?.isAtRisk ?: false
            )
        }
    }
}