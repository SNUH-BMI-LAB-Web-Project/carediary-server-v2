package kr.io.snuhbmilab.carediaryserverv2.admin.dto.response

import kr.io.snuhbmilab.carediaryserverv2.domain.user.entity.User
import kr.io.snuhbmilab.carediaryserverv2.domain.user.entity.UserRiskEvaluation
import java.time.LocalDate
import java.util.UUID

data class AdminUserFindAllResponse(
    val users: List<AdminUserDto>
) {
    companion object {
        @JvmStatic
        fun from(users: List<Pair<User, UserRiskEvaluation?>>) = AdminUserFindAllResponse(
            users = users.map { AdminUserDto.from(it.first, it.second) }
        )
    }

    data class AdminUserDto(
        val userId: UUID,
        val name: String,
        val birthDate: LocalDate,
        val primaryDiagnosis: String?,
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
