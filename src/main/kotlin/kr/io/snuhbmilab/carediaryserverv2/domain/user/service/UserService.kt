package kr.io.snuhbmilab.carediaryserverv2.domain.user.service

import kr.io.snuhbmilab.carediaryserverv2.common.exception.BusinessException
import kr.io.snuhbmilab.carediaryserverv2.domain.user.entity.User
import kr.io.snuhbmilab.carediaryserverv2.domain.user.exception.UserErrorCode
import kr.io.snuhbmilab.carediaryserverv2.domain.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional(readOnly = true)
class UserService(
    private val userRepository: UserRepository,
) {

    fun findByEmail(email: String): User = userRepository.findByEmail(email)
        ?: throw BusinessException(UserErrorCode.USER_NOT_FOUND)

    fun findById(userId: UUID): User = userRepository.findByIdOrNull(userId)
        ?: throw BusinessException(UserErrorCode.USER_NOT_FOUND)
}