package kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.repository

import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.entity.ScaleQuestion
import org.springframework.data.jpa.repository.JpaRepository

interface ScaleQuestionRepository : JpaRepository<ScaleQuestion, Long> {
    /**
 * Retrieve all scale questions ordered by their questionNumber in ascending order.
 *
 * @return A list of ScaleQuestion entities sorted by `questionNumber` ascending.
 */
fun findAllByOrderByQuestionNumberAsc(): List<ScaleQuestion>
}