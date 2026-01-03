package kr.io.snuhbmilab.carediaryserverv2.domain.user.repository

import kr.io.snuhbmilab.carediaryserverv2.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime
import java.util.UUID

interface UserRepository : JpaRepository<User, UUID> {
    fun findByEmail(email: String): User?
    fun findAllByFirstDiaryDateIsNotNullAndTermCountGreaterThan(minTermCount: Int): List<User>
    fun findByEmailAndSocialProviderId(email: String, socialProviderId: User.SocialProviderId): User?
    fun findAllByNameIsNotNull(): List<User>

    @Query("SELECT COUNT(u) FROM User u WHERE u.createdAt >= :startDateTime")
    fun countByCreatedAtAfter(startDateTime: LocalDateTime): Long
}