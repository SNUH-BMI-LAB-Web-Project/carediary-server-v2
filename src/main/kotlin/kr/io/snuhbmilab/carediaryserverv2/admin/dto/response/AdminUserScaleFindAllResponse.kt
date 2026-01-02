package kr.io.snuhbmilab.carediaryserverv2.admin.dto.response

import io.swagger.v3.oas.annotations.media.Schema
import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.constants.ScaleCategory
import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.entity.UserScale
import java.time.LocalDateTime

@Schema(description = "사용자 척도 결과 조회 응답")
data class AdminUserScaleFindAllResponse(
    @Schema(
        description = "회차별 척도 결과 (키: 회차 번호, 값: 해당 회차의 척도 점수 목록)",
        example = "{\"0\": [{\"scaleCategory\": \"ANXIETY\", \"score\": 12}, {\"scaleCategory\": \"DEPRESSION\", \"score\": 8}]}"
    )
    val items: Map<String, List<UserScaleItem>>
) {

    @Schema(description = "사용자 척도 점수 항목")
    data class UserScaleItem(
        @Schema(description = "척도 카테고리 (ANXIETY, DEPRESSION, ANGER)", example = "ANXIETY")
        val scaleCategory: ScaleCategory,

        @Schema(description = "척도 점수", example = "12")
        val score: Int,

        @Schema(description = "척도 점수 생성일")
        val createdAt: LocalDateTime
    ) {
        companion object {
            @JvmStatic
            fun from(userScale: UserScale) = UserScaleItem(
                scaleCategory = userScale.scaleCategory,
                score = userScale.score,
                createdAt = userScale.createdAt
            )
        }
    }

    companion object {
        @JvmStatic
        fun from(userScales: List<UserScale>) = AdminUserScaleFindAllResponse(
            items = userScales.groupBy { it.termCount.toString() }.mapValues { it.value.map(UserScaleItem::from) }
        )
    }
}