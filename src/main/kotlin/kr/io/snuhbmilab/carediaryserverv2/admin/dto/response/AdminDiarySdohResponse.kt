package kr.io.snuhbmilab.carediaryserverv2.admin.dto.response

import kr.io.snuhbmilab.carediaryserverv2.common.utils.parseListFromDBText
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity.Pie

data class AdminDiarySdohResponse(
    val items: List<DiarySdohItemDto>
) {
    companion object {
        @JvmStatic
        fun from(items: List<Pie>) = AdminDiarySdohResponse(
            items = items.map { DiarySdohItemDto.from(it) }
        )
    }

    data class DiarySdohItemDto(
        val elementNo: Short,
        val majorCat: String?,
        val middleCat: String?,
        val subCat: String?,
        val signCode: String?,
        val typeLabel: String?,
        val severity: Short?,
        val duration: Short?,
        val coping: Short?,
        val recommendation: String?,
        val evidences: List<String>
    ) {
        companion object {
            @JvmStatic
            fun from(pie: Pie) = DiarySdohItemDto(
                elementNo = pie.elementNo,
                majorCat = pie.majorCat,
                middleCat = pie.middleCat,
                subCat = pie.subCat,
                signCode = pie.signCode,
                typeLabel = pie.typeLabel,
                severity = pie.severity,
                duration = pie.duration,
                coping = pie.coping,
                recommendation = pie.recommendation,
                evidences = pie.evidences.parseListFromDBText()
            )
        }
    }
}
