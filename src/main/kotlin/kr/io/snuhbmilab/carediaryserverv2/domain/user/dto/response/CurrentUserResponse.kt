package kr.io.snuhbmilab.carediaryserverv2.domain.user.dto.response

import kr.io.snuhbmilab.carediaryserverv2.common.constants.Role
import kr.io.snuhbmilab.carediaryserverv2.domain.user.entity.User
import kr.io.snuhbmilab.carediaryserverv2.domain.user.entity.UserInformation
import java.time.LocalDate
import java.time.YearMonth
import java.util.UUID

data class CurrentUserResponse(
    val userId: UUID,
    val email: String,
    val name: String,
    val role: Role,
    val gender: User.Gender,
    val birthDate: LocalDate,
    val address: String,
    val primaryDiagnosis: String?,
    val educationBeforeOnset: String?,
    val previousDiagnosis: String?,
    val diagnosisYearMonth: YearMonth?,
    val diagnosisHospital: String?,
    val chiefComplaint: String?,
    val currentHospital: String?,
    val currentResidence: String?
) {
    companion object {
        @JvmStatic
        fun of(user: User, userInformation: UserInformation?): CurrentUserResponse {
            return CurrentUserResponse(
                userId = user.id!!,
                email = user.email,
                name = user.name!!,
                role = user.role,
                gender = user.gender!!,
                birthDate = user.birthDate!!,
                address = user.address!!,
                primaryDiagnosis = user.primaryDiagnosis,
                educationBeforeOnset = userInformation?.educationBeforeOnset,
                previousDiagnosis = userInformation?.previousDiagnosis,
                diagnosisYearMonth = userInformation?.diagnosisYearMonth,
                diagnosisHospital = userInformation?.diagnosisHospital,
                chiefComplaint = userInformation?.chiefComplaint,
                currentHospital = userInformation?.currentHospital,
                currentResidence = userInformation?.currentResidence
            )
        }
    }
}
