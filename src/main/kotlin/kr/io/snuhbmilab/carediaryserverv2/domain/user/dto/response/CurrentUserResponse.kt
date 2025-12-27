package kr.io.snuhbmilab.carediaryserverv2.domain.user.dto.response

import io.swagger.v3.oas.annotations.media.Schema
import kr.io.snuhbmilab.carediaryserverv2.common.constants.Role
import kr.io.snuhbmilab.carediaryserverv2.domain.user.entity.User
import kr.io.snuhbmilab.carediaryserverv2.domain.user.entity.UserInformation
import java.time.LocalDate
import java.time.YearMonth
import java.util.UUID

@Schema(description = "현재 사용자 정보 응답")
data class CurrentUserResponse(
    @Schema(description = "사용자 ID", example = "550e8400-e29b-41d4-a716-446655440000")
    val userId: UUID,

    @Schema(description = "이메일", example = "user@example.com")
    val email: String,

    @Schema(description = "이름", example = "홍길동")
    val name: String,

    @Schema(description = "역할", example = "USER")
    val role: Role,

    @Schema(description = "성별", example = "MALE")
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