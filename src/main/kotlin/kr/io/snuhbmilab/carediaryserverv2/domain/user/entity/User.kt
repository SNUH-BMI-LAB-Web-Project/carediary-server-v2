package kr.io.snuhbmilab.carediaryserverv2.domain.user.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.io.snuhbmilab.carediaryserverv2.common.constants.PROVIDER_ID_PATTERN
import kr.io.snuhbmilab.carediaryserverv2.common.constants.Role
import kr.io.snuhbmilab.carediaryserverv2.common.entity.BaseTimeEntity
import java.time.LocalDate
import java.util.UUID

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id", nullable = false)
    val id: UUID? = null,

    @Column(nullable = false)
    val email: String,

    @Column
    val name: String? = null,

    @Column(name = "social_provider_id", nullable = false)
    val socialProviderId: SocialProviderId,

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    val role: Role,

    @Enumerated(EnumType.STRING)
    @Column
    var gender: Gender? = null,

    @Column(name = "birth_date")
    var birthDate: LocalDate? = null,

    var address: String? = null,

    @Column(name = "primay_diagnosis")
    val primaryDiagnosis: String? = null,
) : BaseTimeEntity() {

    @JvmInline
    value class SocialProviderId(val value: String) {
        init {
            require(PROVIDER_ID_PATTERN.matches(value)) { "올바르지 않은 소셜 Provider ID 형식입니다." }
        }

        val socialProvider: String
            get() = value.split("-")[0]

        val socialId: String
            get() = value.split("-")[1]
    }

    enum class Gender {
        MALE, FEMALE
    }
}