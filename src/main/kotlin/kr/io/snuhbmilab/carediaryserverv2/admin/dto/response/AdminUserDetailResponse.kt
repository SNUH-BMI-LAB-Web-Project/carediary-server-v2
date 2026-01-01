package kr.io.snuhbmilab.carediaryserverv2.admin.dto.response

import kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity.Diary
import kr.io.snuhbmilab.carediaryserverv2.domain.user.entity.User
import java.time.LocalDate
import java.util.UUID

data class AdminUserDetailResponse(
    val userId: UUID,
    val name: String,
    val birthDate: LocalDate,
    val primaryDiagnosis: String?,
    val diaryDates: List<LocalDate>,
    val monthlyDiaryCount: Int,
    val yearlyDiaryCount: Int,
    val emotionCounts: Map<Diary.Emotion, Int>
) {
    companion object {
        @JvmStatic
        fun of(
            user: User,
            diaryDates: List<LocalDate>,
            monthlyDiaryCount: Int,
            yearlyDiaryCount: Int,
            emotionCounts: Map<Diary.Emotion, Int>
        ) = AdminUserDetailResponse(
            user.id!!,
            user.name!!,
            user.birthDate!!,
            user.primaryDiagnosis,
            diaryDates,
            monthlyDiaryCount,
            yearlyDiaryCount,
            emotionCounts
        )
    }
}
