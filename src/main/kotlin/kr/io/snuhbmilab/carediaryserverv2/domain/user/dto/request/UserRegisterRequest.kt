
package kr.io.snuhbmilab.carediaryserverv2.domain.user.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import kr.io.snuhbmilab.carediaryserverv2.common.constants.Role
import kr.io.snuhbmilab.carediaryserverv2.domain.user.entity.User
import kr.io.snuhbmilab.carediaryserverv2.domain.user.entity.UserInformation
import java.time.LocalDate
import java.time.YearMonth

@Schema(description = "회원가입 요청")
data class UserRegisterRequest(
    @Schema(description = "이름", example = "홍길동")
    val name: String,

    @Schema(description = "역할 (USER, ADMIN)", example = "USER")
    val role: Role,

    @Schema(description = "성별 (MALE, FEMALE)", example = "MALE")
    val gender: User.Gender,

    @Schema(description = "생년월일", example = "1990-01-15")
    val birthDate: LocalDate,

    @Schema(description = "주소", example = "서울특별시 강남구 테헤란로 123")
    val address: String,

    @Schema(description = "[환자 정보] 주 진단명", example = "조현병")
    val primaryDiagnosis: String?,

    @Schema(description = "[환자 정보] 학력 (발병 전)", example = "대학교 졸업")
    val educationBeforeOnset: String?,

    @Schema(description = "[환자 정보] 병력 - 진단명 (과거 진단명)", example = "우울증")
    val previousDiagnosis: String?,

    @Schema(description = "[환자 정보] 병력 - 진단받은 시기", example = "2020-06")
    val diagnosisYearMonth: YearMonth?,

    @Schema(description = "[환자 정보] 병력 - 진단받은 병원", example = "서울대학교병원")
    val diagnosisHospital: String?,

    @Schema(description = "[환자 정보] 주증상", example = "환청, 망상")
    val chiefComplaint: String?,

    @Schema(description = "[환자 정보] 현재 주로 이용하는 병원", example = "서울대학교병원 정신건강의학과")
    val currentHospital: String?,

    @Schema(description = "[환자 정보] 현재 거주하는 장소", example = "자택")
    val currentResidence: String?,

    @Schema(description = "[환자 정보] 의료보장 (HEALTH_INSURANCE: 건강보험, MEDICAL_AID_1: 의료급여 1종, MEDICAL_AID_2: 의료급여 2종, NEAR_POOR_1: 차상위 1종, NEAR_POOR_2: 차상위 2종)", example = "HEALTH_INSURANCE")
    val medicalCoverage: UserInformation.MedicalCoverage?,

    @Schema(description = "[환자 정보] 산정특례 등록 여부", example = "true")
    val specialCaseRegistered: Boolean?,

    @Schema(description = "[환자 정보] 산정특례 등록일 (산정특례 등록 시 필수)", example = "2021-03-01")
    val specialCaseRegisteredDate: LocalDate?,

    @Schema(description = "[환자 정보] 장애 등록 여부", example = "true")
    val disabilityRegistered: Boolean?,

    @Schema(description = "[환자 정보] 장애 등록 진행 상태 (REGISTERED: 등록, IN_PROGRESS: 진행 중, NOT_REGISTERED: 미등록)", example = "REGISTERED")
    val disabilityStatus: UserInformation.DisabilityStatus?,

    @Schema(description = "[환자 정보] 장애 종류 (장애 등록 시 필수)", example = "정신장애")
    val disabilityType: String?,

    @Schema(description = "[환자 정보] 장애 정도 (SEVERE: 심한 장애, NOT_SEVERE: 심하지 않은 장애)", example = "NOT_SEVERE")
    val disabilitySeverity: UserInformation.DisabilitySeverity?,

    @Schema(description = "[환자 정보] 사회복지서비스 (다중 선택)", example = "[\"CAREGIVER_COST\", \"SPECIAL_DIET_PURCHASE\"]")
    val socialWelfareServiceLabels: List<String>?,
)