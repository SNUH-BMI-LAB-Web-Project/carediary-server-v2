package kr.io.snuhbmilab.carediaryserverv2.domain.user.facade

import kr.io.snuhbmilab.carediaryserverv2.auth.jwt.service.JwtProvider
import kr.io.snuhbmilab.carediaryserverv2.common.constants.Role
import kr.io.snuhbmilab.carediaryserverv2.common.exception.BusinessException
import kr.io.snuhbmilab.carediaryserverv2.common.properties.VerificationCodeProperties
import kr.io.snuhbmilab.carediaryserverv2.domain.user.dto.request.UserRegisterRequest
import kr.io.snuhbmilab.carediaryserverv2.domain.user.dto.response.CurrentUserResponse
import kr.io.snuhbmilab.carediaryserverv2.domain.user.dto.response.UserRegisterResponse
import kr.io.snuhbmilab.carediaryserverv2.domain.user.exception.UserErrorCode
import kr.io.snuhbmilab.carediaryserverv2.domain.user.service.UserInformationService
import kr.io.snuhbmilab.carediaryserverv2.domain.user.service.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional(readOnly = true)
class UserFacade(
    private val userService: UserService,
    private val userInformationService: UserInformationService,
    private val jwtProvider: JwtProvider,
    private val verificationCodeProperties: VerificationCodeProperties,
) {
    @Transactional
    fun register(userId: UUID, request: UserRegisterRequest): UserRegisterResponse {
        val user = userService.findById(userId)

        if (request.role == Role.ADMIN || request.role == Role.CARE_MANAGER) {
            validateVerificationCode(request.role, request.adminCode)
        }

        user.register(
            request.name,
            request.role,
            request.gender,
            request.birthDate,
            request.address,
            request.primaryDiagnosis,
            request.managerId
        )

        if (!user.isAdminOrManager()) {
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

        val accessToken = jwtProvider.generateToken(user)

        return UserRegisterResponse.from(accessToken)
    }

    private fun validateVerificationCode(role: Role, adminCode: String?) {
        val expectedCode = when (role) {
            Role.ADMIN -> verificationCodeProperties.admin
            Role.CARE_MANAGER -> verificationCodeProperties.careManager
            else -> throw IllegalStateException()
        }
        if (adminCode != expectedCode) {
            throw BusinessException(UserErrorCode.INVALID_VERIFICATION_CODE)
        }
    }

    fun getMe(userId: UUID): CurrentUserResponse {
        val user = userService.findById(userId)
        val userInformation = if (user.isAdminOrManager()) null else userInformationService.findByUserId(userId)

        return CurrentUserResponse.of(user, userInformation)
    }
}