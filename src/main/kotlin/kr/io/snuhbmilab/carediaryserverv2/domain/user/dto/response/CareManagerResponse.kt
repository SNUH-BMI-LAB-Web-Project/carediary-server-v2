package kr.io.snuhbmilab.carediaryserverv2.domain.user.dto.response

import kr.io.snuhbmilab.carediaryserverv2.domain.user.entity.User
import java.util.UUID

data class CareManagerResponse(
    val managerId: UUID,
    val name: String,
    val email: String,
) {
    companion object {
        fun from(user: User) = CareManagerResponse(
            managerId = user.id!!,
            name = user.name!!,
            email = user.email,
        )
    }
}
