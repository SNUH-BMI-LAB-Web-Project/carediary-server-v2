package kr.io.snuhbmilab.carediaryserverv2.admin.dto.response

import io.swagger.v3.oas.annotations.media.Schema
import kr.io.snuhbmilab.carediaryserverv2.common.utils.parseListFromDBText
import kr.io.snuhbmilab.carediaryserverv2.domain.diary.entity.Pie

@Schema(description = "관리자 일기 SDOH 조회 응답")
data class AdminDiarySdohResponse(
    @Schema(description = "SDOH 항목 목록")
    val items: List<DiarySdohItemDto>
) {
    companion object {
        @JvmStatic
        fun from(items: List<Pie>) = AdminDiarySdohResponse(
            items = items.map { DiarySdohItemDto.from(it) }
        )
    }

    @Schema(description = "일기 SDOH 항목 정보")
    data class DiarySdohItemDto(
        @Schema(description = "요소 번호", example = "1")
        val elementNo: Short,

        @Schema(description = "대분류", example = "경제적 안정")
        val majorCat: String?,

        @Schema(description = "중분류", example = "고용")
        val middleCat: String?,

        @Schema(description = "소분류", example = "실업")
        val subCat: String?,

        @Schema(description = "징후 코드", example = "ES001")
        val signCode: String?,

        @Schema(description = "유형 라벨", example = "경제적 어려움")
        val typeLabel: String?,

        @Schema(description = "심각도 (1-5)", example = "3")
        val severity: Short?,

        @Schema(description = "지속 기간 (1-5)", example = "4")
        val duration: Short?,

        @Schema(description = "대처 능력 (1-5)", example = "2")
        val coping: Short?,

        @Schema(description = "권장 사항", example = "취업 지원 서비스 연결 필요")
        val recommendation: String?,

        @Schema(description = "근거 문장 목록", example = "[\"실직 상태가 6개월째 지속되고 있다\", \"생활비가 부족하여 어려움을 겪고 있다\"]")
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