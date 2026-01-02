package kr.io.snuhbmilab.carediaryserverv2.admin.dto.response

import io.swagger.v3.oas.annotations.media.Schema
import kr.io.snuhbmilab.carediaryserverv2.common.utils.parseListFromDBText
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity.DiaryWelfareServiceEntity
import java.util.UUID

@Schema(description = "관리자 일기 복지 서비스 조회 응답")
data class AdminDiaryWelfareServiceResponse(
    @Schema(description = "일기 ID", example = "550e8400-e29b-41d4-a716-446655440000")
    val diaryId: UUID,

    @Schema(description = "매칭된 복지 서비스 목록")
    val welfareServices: List<DiaryWelfareServiceDto>
) {
    companion object {
        @JvmStatic
        fun of(diaryId: UUID, welfareServices: List<DiaryWelfareServiceEntity>) = AdminDiaryWelfareServiceResponse(
            diaryId = diaryId,
            welfareServices = welfareServices.map { DiaryWelfareServiceDto.from(it) }
        )
    }

    @Schema(description = "일기 복지 서비스 정보")
    data class DiaryWelfareServiceDto(
        @Schema(description = "복지 서비스 ID", example = "1")
        val welfareServiceId: Long,

        @Schema(description = "서비스 범위 (CENTRAL: 중앙정부, LOCAL: 지방정부)", example = "CENTRAL")
        val serviceScope: DiaryWelfareServiceEntity.ServiceScope,

        @Schema(description = "서비스 고유 ID", example = "WS2024001")
        val serviceId: String,

        @Schema(description = "서비스명", example = "정신건강복지센터 상담 서비스")
        val serviceName: String,

        @Schema(description = "서비스 상세 링크", example = "https://www.bokjiro.go.kr/service/12345")
        val serviceDetailLink: String,

        @Schema(description = "서비스 요약 설명", example = "정신건강 상담 및 사례관리 서비스를 제공합니다.")
        val serviceDigest: String?,

        @Schema(description = "행정구역 1단계 (시/도)", example = "서울특별시")
        val adminLevel1Name: String?,

        @Schema(description = "행정구역 2단계 (시/군/구)", example = "강남구")
        val adminLevel2Name: String?,

        @Schema(description = "매칭된 생애주기 키워드 목록", example = "[\"청년\", \"중장년\"]")
        val matchedLifeCycleKeywords: List<String>,

        @Schema(description = "매칭된 가구상황 키워드 목록", example = "[\"1인 가구\", \"저소득층\"]")
        val matchedHouseholdStatusKeywords: List<String>,

        @Schema(description = "매칭된 관심사 키워드 목록", example = "[\"정신건강\", \"심리상담\"]")
        val matchedInterestKeywords: List<String>,

        @Schema(description = "사용자에게 표시 여부", example = "true")
        val visible: Boolean
    ) {
        companion object {
            @JvmStatic
            fun from(entity: DiaryWelfareServiceEntity) = DiaryWelfareServiceDto(
                welfareServiceId = entity.id!!,
                serviceScope = entity.serviceScope,
                serviceId = entity.serviceId,
                serviceName = entity.serviceName,
                serviceDetailLink = entity.serviceDetailLink,
                serviceDigest = entity.serviceDigest,
                adminLevel1Name = entity.adminLevel1Name,
                adminLevel2Name = entity.adminLevel2Name,
                matchedLifeCycleKeywords = entity.matchedLifeCycleKeywords?.parseListFromDBText() ?: emptyList(),
                matchedHouseholdStatusKeywords = entity.matchedHouseholdStatusKeywords?.parseListFromDBText()
                    ?: emptyList(),
                matchedInterestKeywords = entity.matchedInterestKeywords?.parseListFromDBText() ?: emptyList(),
                visible = entity.visible
            )
        }
    }
}