package kr.io.snuhbmilab.carediaryserverv2.domain.user.repository

import kr.io.snuhbmilab.carediaryserverv2.domain.user.entity.UserInformation
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserInformationRepository : JpaRepository<UserInformation, Long> {
    fun findByUserId(userId: UUID): UserInformation?
}