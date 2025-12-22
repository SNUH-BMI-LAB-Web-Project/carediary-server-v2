package kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.dto.response

import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.constants.ScaleCategory
import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.entity.UserScale

data class UserScaleFindAllResponse(
    val items: Map<String, List<UserScaleItem>>
) {

    data class UserScaleItem(
        val scaleCategory: ScaleCategory,
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
