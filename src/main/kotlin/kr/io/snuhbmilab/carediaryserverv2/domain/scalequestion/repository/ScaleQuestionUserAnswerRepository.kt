package kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.repository

import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.entity.ScaleQuestionUserAnswer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface ScaleQuestionUserAnswerRepository : JpaRepository<ScaleQuestionUserAnswer, Long> {
    @Query(
        """
        SELECT s
        FROM ScaleQuestionUserAnswer s
        JOIN FETCH s.question sq
        WHERE s.userId = :userId
        AND s.termCount = :termCount
        ORDER BY sq.questionNumber ASC
        """
    )
    fun findAllByUserIdAndTermCountWithScaleQuestion(userId: UUID, termCount: Int): List<ScaleQuestionUserAnswer>
}