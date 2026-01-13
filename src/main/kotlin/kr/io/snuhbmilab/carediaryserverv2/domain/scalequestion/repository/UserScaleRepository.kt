package kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.repository

import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.constants.ScaleCategory
import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.entity.UserScale
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserScaleRepository : JpaRepository<UserScale, Long> {
    fun findAllByUserId(userId: UUID): List<UserScale>

    fun findAllByUserIdAndTermCount(userId: UUID, termCount: Int): List<UserScale>

    fun existsByUserIdAndTermCount(userId: UUID, termCount: Int): Boolean

    fun existsByUserIdAndTermCountAndScaleCategoryAndScoreGreaterThanEqual(userId: UUID, termCount: Int, scaleCategory: ScaleCategory, score: Int): Boolean

    fun findByUserIdAndTermCountAndScaleCategory(userId: UUID, termCount: Int, scaleCategory: ScaleCategory): UserScale?
}