package kr.io.snuhbmilab.carediaryserverv2.domain.user.dto.request

import kr.io.snuhbmilab.carediaryserverv2.common.constants.Role
import kr.io.snuhbmilab.carediaryserverv2.domain.user.entity.User
import kr.io.snuhbmilab.carediaryserverv2.domain.user.entity.UserInformation
import java.time.LocalDate
import java.time.YearMonth

data class UserRegisterRequest(
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
    val currentResidence: String?,
    val medicalCoverage: UserInformation.MedicalCoverage?,
    val specialCaseRegistered: Boolean?,
    val specialCaseRegisteredDate: LocalDate?,
    val disabilityRegistered: Boolean?,
    val disabilityStatus: UserInformation.DisabilityStatus?,
    val disabilityType: String?,
    val disabilitySeverity: UserInformation.DisabilitySeverity?,
    val socialWelfareServiceLabels: List<String>?,
)
