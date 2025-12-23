package kr.io.snuhbmilab.carediaryserverv2.domain.home.dto.response

import kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity.Diary
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity.DiaryWelfareServiceEntity

data class HomeResponse(
    val monthlyDiaryCount: Int,
    val yearlyDiaryCount: Int,
    val emotionCounts: Map<Diary.Emotion, Int>,
    val recommendedWelfareServices: List<WelfareServiceItem>,
    val isScaleQuestionRequired: Boolean,
    val termCount: Int,
) {
    data class WelfareServiceItem(
        val serviceName: String,
        val serviceDetailLink: String,
        val serviceDigest: String?
    ) {
        companion object {
            @JvmStatic
            fun from(diaryWelfareServiceEntity: DiaryWelfareServiceEntity) = WelfareServiceItem(
                serviceName = diaryWelfareServiceEntity.serviceName,
                serviceDetailLink = diaryWelfareServiceEntity.serviceDetailLink,
                serviceDigest = diaryWelfareServiceEntity.serviceDigest
            )
        }
    }

    companion object {
        @JvmStatic
        fun of(
            monthlyDiaryCount: Int,
            yearlyDiaryCount: Int,
            emotionCounts: Map<Diary.Emotion, Int>,
            recommendedWelfareServices: List<DiaryWelfareServiceEntity>,
            isScaleQuestionRequired: Boolean,
            termCount: Int
        ) = HomeResponse(
            monthlyDiaryCount = monthlyDiaryCount,
            yearlyDiaryCount = yearlyDiaryCount,
            emotionCounts = emotionCounts,
            recommendedWelfareServices = recommendedWelfareServices.map { WelfareServiceItem.from(it) },
            isScaleQuestionRequired = isScaleQuestionRequired,
            termCount = termCount
        )
    }
}
