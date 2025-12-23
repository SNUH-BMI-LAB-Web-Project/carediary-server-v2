package kr.io.snuhbmilab.carediaryserverv2.domain.user.repository

import kr.io.snuhbmilab.carediaryserverv2.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserRepository : JpaRepository<User, UUID> {
    fun findByEmail(email: String): User?

    fun findAllByFirstDiaryDateIsNotNullAndTermCountGreaterThan(minTermCount: Int): List<User>
}