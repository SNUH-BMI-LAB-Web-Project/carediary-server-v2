package kr.io.snuhbmilab.carediaryserverv2.admin.dto.response

import io.swagger.v3.oas.annotations.media.Schema
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity.Diary
import kr.io.snuhbmilab.carediaryserverv2.domain.user.entity.User
import java.time.LocalDate
import java.util.UUID

@Schema(description = "관리자 사용자 상세 조회 응답")
data class AdminUserDetailResponse(
    @Schema(description = "사용자 ID", example = "550e8400-e29b-41d4-a716-446655440000")
    val userId: UUID,

    @Schema(description = "이름", example = "홍길동")
    val name: String,

    @Schema(description = "생년월일", example = "1990-01-15")
    val birthDate: LocalDate,

    @Schema(description = "주 진단명", example = "조현병")
    val primaryDiagnosis: String?,

    @Schema(description = "일기 작성 날짜 목록", example = "[\"2024-01-15\", \"2024-01-20\", \"2024-01-25\"]")
    val diaryDates: List<LocalDate>,

    @Schema(description = "이번 달 작성한 일기 수", example = "12")
    val monthlyDiaryCount: Int,

    @Schema(description = "올해 작성한 일기 수", example = "85")
    val yearlyDiaryCount: Int,

    @Schema(description = "감정별 일기 수 (HAPPY, LOVE, SAD)", example = "{\"HAPPY\": 30, \"LOVE\": 25, \"SAD\": 30}")
    val emotionCounts: Map<Diary.Emotion, Int>,

    @Schema(description = "위험 평가 사유", example = "최근 2주간 부정적 감정 일기 비율 증가")
    val riskReason: String?
) {
    companion object {
        @JvmStatic
        fun of(
            user: User,
            diaryDates: List<LocalDate>,
            monthlyDiaryCount: Int,
            yearlyDiaryCount: Int,
            emotionCounts: Map<Diary.Emotion, Int>,
            riskReason: String? = null
        ) = AdminUserDetailResponse(
            user.id!!,
            user.name!!,
            user.birthDate!!,
            user.primaryDiagnosis,
            diaryDates,
            monthlyDiaryCount,
            yearlyDiaryCount,
            emotionCounts,
            riskReason
        )
    }
}