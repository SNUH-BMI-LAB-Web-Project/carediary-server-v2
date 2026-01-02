package kr.io.snuhbmilab.carediaryserverv2.admin.dto.response

import kr.io.snuhbmilab.carediaryserverv2.common.utils.parseListFromDBText
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity.DiaryWelfareServiceEntity
import java.util.UUID

data class AdminDiaryWelfareServiceResponse(
    val diaryId: UUID,
    val welfareServices: List<DiaryWelfareServiceDto>
) {
    companion object {
        @JvmStatic
        fun of(diaryId: UUID, welfareServices: List<DiaryWelfareServiceEntity>) = AdminDiaryWelfareServiceResponse(
            diaryId = diaryId,
            welfareServices = welfareServices.map { DiaryWelfareServiceDto.from(it) }
        )
    }

    data class DiaryWelfareServiceDto(
        val welfareServiceId: Long,
        val serviceScope: DiaryWelfareServiceEntity.ServiceScope,
        val serviceId: String,
        val serviceName: String,
        val serviceDetailLink: String,
        val serviceDigest: String?,
        val adminLevel1Name: String?,
        val adminLevel2Name: String?,
        val matchedLifeCycleKeywords: List<String>,
        val matchedHouseholdStatusKeywords: List<String>,
        val matchedInterestKeywords: List<String>,
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
