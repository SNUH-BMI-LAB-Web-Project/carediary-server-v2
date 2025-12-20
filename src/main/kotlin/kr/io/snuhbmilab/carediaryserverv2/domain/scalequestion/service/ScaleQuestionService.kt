package kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.service

import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.entity.ScaleQuestionUserAnswer
import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.repository.ScaleQuestionRepository
import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.repository.ScaleQuestionUserAnswerRepository
import kr.io.snuhbmilab.carediaryserverv2.domain.user.entity.User
import org.springframework.stereotype.Service

@Service
class ScaleQuestionService(
    private val scaleQuestionRepository: ScaleQuestionRepository,
    private val scaleQuestionUserAnswerRepository: ScaleQuestionUserAnswerRepository
) {
    /**
 * Retrieves all scale questions ordered by their question number in ascending order.
 *
 * @return A list of ScaleQuestion objects sorted by `questionNumber` ascending.
 */
fun findAll() = scaleQuestionRepository.findAllByOrderByQuestionNumberAsc()

    /**
     * Persists a user's answer for a specific scale question.
     *
     * @param user The user who provided the answer.
     * @param scaleQuestionId The identifier of the scale question being answered.
     * @param userAnswer The answer value provided by the user.
     */
    fun appendUserAnswer(user: User, scaleQuestionId: Long, userAnswer: String) {
        scaleQuestionUserAnswerRepository.save(
            ScaleQuestionUserAnswer(
                user = user,
                scaleQuestionId = scaleQuestionId,
                userAnswer = userAnswer
            )
        )
    }
}