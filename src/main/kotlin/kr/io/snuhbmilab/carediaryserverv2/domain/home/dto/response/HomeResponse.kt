package kr.io.snuhbmilab.carediaryserverv2.domain.home.dto.response

import io.swagger.v3.oas.annotations.media.Schema
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity.Diary
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity.DiaryWelfareServiceEntity

@Schema(description = "홈 화면 정보 응답")
data class HomeResponse(
    @Schema(description = "이번 달 작성한 일기 수", example = "12")
    val monthlyDiaryCount: Int,

    @Schema(description = "올해 작성한 일기 수", example = "85")
    val yearlyDiaryCount: Int,

    @Schema(description = "감정별 일기 수 (HAPPY, LOVE, SAD)", example = "{\"HAPPY\": 30, \"LOVE\": 25, \"SAD\": 30}")
    val emotionCounts: Map<Diary.Emotion, Int>,

    @Schema(description = "추천 복지 서비스 목록")
    val recommendedWelfareServices: List<WelfareServiceItem>,

    @Schema(description = "척도 질문 응답 필요 여부", example = "true")
    val isScaleQuestionRequired: Boolean,

    @Schema(description = "현재 회기 수", example = "3")
    val termCount: Int,
) {
    @Schema(description = "복지 서비스 항목")
    data class WelfareServiceItem(
        @Schema(description = "서비스명", example = "정신건강복지센터 상담 서비스")
        val serviceName: String,

        @Schema(description = "서비스 상세 링크", example = "https://www.bokjiro.go.kr/service/12345")
        val serviceDetailLink: String,

        @Schema(description = "서비스 요약 설명", example = "정신건강 상담 및 사례관리 서비스를 제공합니다.")
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