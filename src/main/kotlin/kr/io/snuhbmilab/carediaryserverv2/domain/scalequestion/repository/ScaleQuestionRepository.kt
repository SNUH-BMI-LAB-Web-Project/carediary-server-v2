package kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.repository

import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.entity.ScaleQuestion
import org.springframework.data.jpa.repository.JpaRepository

interface ScaleQuestionRepository : JpaRepository<ScaleQuestion, Long> {
    fun findAllByOrderByQuestionNumberAsc(): List<ScaleQuestion>
}