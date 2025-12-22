package kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.repository

import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.entity.UserScale
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserScaleRepository : JpaRepository<UserScale, Long> {
    fun findAllByUserId(userId: UUID): List<UserScale>
}