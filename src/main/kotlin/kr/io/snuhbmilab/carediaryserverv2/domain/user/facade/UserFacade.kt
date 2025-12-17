package kr.io.snuhbmilab.carediaryserverv2.domain.user.facade

import kr.io.snuhbmilab.carediaryserverv2.domain.user.dto.request.UserRegisterRequest
import kr.io.snuhbmilab.carediaryserverv2.domain.user.dto.response.CurrentUserResponse
import kr.io.snuhbmilab.carediaryserverv2.domain.user.service.UserInformationService
import kr.io.snuhbmilab.carediaryserverv2.domain.user.service.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional(readOnly = true)
class UserFacade(
    private val userService: UserService,
    private val userInformationService: UserInformationService
) {
    @Transactional
    fun register(userId: UUID, request: UserRegisterRequest) {
        val user = userService.findById(userId)

        user.register(
            request.name,
            request.role,
            request.gender,
            request.birthDate,
            request.address,
            request.primaryDiagnosis
        )

        if (user.isAdmin()) return

        userInformationService.create(
            user,
            request.educationBeforeOnset,
            request.previousDiagnosis,
            request.diagnosisYearMonth,
            request.diagnosisHospital,
            request.chiefComplaint,
            request.currentHospital,
            request.currentResidence
        )
    }

    fun getMe(userId: UUID): CurrentUserResponse {
        val user = userService.findById(userId)
        val userInformation = if (user.isAdmin()) null else userInformationService.findByUserId(userId)

        return CurrentUserResponse.of(user, userInformation)
    }
}