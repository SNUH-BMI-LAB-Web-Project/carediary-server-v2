package kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.dto.response

import io.swagger.v3.oas.annotations.media.Schema
import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.constants.ScaleCategory
import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.entity.UserScale

@Schema(description = "사용자 척도 결과 조회 응답")
data class UserScaleFindAllResponse(
    @Schema(
        description = "회차별 척도 결과 (키: 회차 번호, 값: 해당 회차의 척도 점수 목록)",
        example = "{\"0\": [{\"scaleCategory\": \"ANXIETY_DEPRESSION\", \"score\": 12}, {\"scaleCategory\": \"ANGER\", \"score\": 8}]}"
    )
    val items: Map<String, List<UserScaleItem>>
) {

    @Schema(description = "사용자 척도 점수 항목")
    data class UserScaleItem(
        @Schema(description = "척도 카테고리 (ANXIETY_DEPRESSION, ANGER)", example = "ANGER")
        val scaleCategory: ScaleCategory,

        @Schema(description = "척도 점수", example = "12")
        val score: Int
    ) {
        companion object {
            @JvmStatic
            fun from(userScale: UserScale) = UserScaleItem(
                scaleCategory = userScale.scaleCategory,
                score = userScale.score
            )
        }
    }

    companion object {
        @JvmStatic
        fun from(userScales: List<UserScale>) = UserScaleFindAllResponse(
            items = userScales.groupBy { it.termCount.toString() }.mapValues { it.value.map(UserScaleItem::from) }
        )
    }
}