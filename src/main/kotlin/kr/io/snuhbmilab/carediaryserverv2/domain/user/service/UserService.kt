package kr.io.snuhbmilab.carediaryserverv2.domain.user.service

import kr.io.snuhbmilab.carediaryserverv2.common.constants.Role
import kr.io.snuhbmilab.carediaryserverv2.common.exception.BusinessException
import kr.io.snuhbmilab.carediaryserverv2.domain.user.entity.User
import kr.io.snuhbmilab.carediaryserverv2.domain.user.exception.UserErrorCode
import kr.io.snuhbmilab.carediaryserverv2.domain.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.UUID

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    fun findOrCreate(email: String, provider: String, socialId: String): User {
        val socialProviderId = User.SocialProviderId("${provider}-${socialId}")

        return userRepository.findByEmailAndSocialProviderId(email, socialProviderId)
            ?: userRepository.save(
                User(
                    email = email,
                    socialProviderId = socialProviderId,
                    role = Role.USER
                )
            )
    }

    fun findByEmail(email: String): User? = userRepository.findByEmail(email)

    fun findById(userId: UUID): User = userRepository.findByIdOrNull(userId)
        ?: throw BusinessException(UserErrorCode.USER_NOT_FOUND)

    @Transactional
    fun updateFirstDiaryDate(user: User, date: LocalDate) {
        user.firstDiaryDate = date
        user.termCount = 1
    }
}