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
            user = user,
            educationBeforeOnset = request.educationBeforeOnset,
            previousDiagnosis = request.previousDiagnosis,
            diagnosisYearMonth = request.diagnosisYearMonth,
            diagnosisHospital = request.diagnosisHospital,
            chiefComplaint = request.chiefComplaint,
            currentHospital = request.currentHospital,
            currentResidence = request.currentResidence,
            medicalCoverage = request.medicalCoverage,
            specialCaseRegistered = request.specialCaseRegistered,
            specialCaseRegisteredDate = request.specialCaseRegisteredDate,
            disabilityRegistered = request.disabilityRegistered,
            disabilityStatus = request.disabilityStatus,
            disabilityType = request.disabilityType,
            disabilitySeverity = request.disabilitySeverity,
            socialWelfareServiceLabels = request.socialWelfareServiceLabels
        )
    }

    fun getMe(userId: UUID): CurrentUserResponse {
        val user = userService.findById(userId)
        val userInformation = if (user.isAdmin()) null else userInformationService.findByUserId(userId)

        return CurrentUserResponse.of(user, userInformation)
    }
}