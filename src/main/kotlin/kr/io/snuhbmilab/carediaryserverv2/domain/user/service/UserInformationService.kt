package kr.io.snuhbmilab.carediaryserverv2.domain.user.service

import kr.io.snuhbmilab.carediaryserverv2.common.exception.BusinessException
import kr.io.snuhbmilab.carediaryserverv2.domain.user.entity.User
import kr.io.snuhbmilab.carediaryserverv2.domain.user.entity.UserInformation
import kr.io.snuhbmilab.carediaryserverv2.domain.user.exception.UserErrorCode
import kr.io.snuhbmilab.carediaryserverv2.domain.user.repository.UserInformationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.YearMonth
import java.util.UUID

@Service
class UserInformationService(
    private val userInformationRepository: UserInformationRepository
) {
    @Transactional
    fun create(
        user: User,
        educationBeforeOnset: String?,
        previousDiagnosis: String?,
        diagnosisYearMonth: YearMonth?,
        diagnosisHospital: String?,
        chiefComplaint: String?,
        currentHospital: String?,
        currentResidence: String?
    ): UserInformation = userInformationRepository.save(
        UserInformation(
            user = user,
            educationBeforeOnset = educationBeforeOnset,
            previousDiagnosis = previousDiagnosis,
            diagnosisYearMonth = diagnosisYearMonth,
            diagnosisHospital = diagnosisHospital,
            chiefComplaint = chiefComplaint,
            currentHospital = currentHospital,
            currentResidence = currentResidence
        )
    )

    fun findByUserId(userId: UUID): UserInformation = userInformationRepository.findByUserId(userId)
            ?: throw BusinessException(UserErrorCode.USER_INFORMATION_NOT_FOUND)
}