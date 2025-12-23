package kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.service

import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.entity.ScaleQuestion
import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.entity.ScaleQuestionUserAnswer
import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.repository.ScaleQuestionRepository
import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.repository.ScaleQuestionUserAnswerRepository
import kr.io.snuhbmilab.carediaryserverv2.domain.scalequestion.repository.UserScaleRepository
import kr.io.snuhbmilab.carediaryserverv2.domain.user.entity.User
import org.springframework.stereotype.Service

@Service
class ScaleQuestionService(
    private val scaleQuestionRepository: ScaleQuestionRepository,
    private val scaleQuestionUserAnswerRepository: ScaleQuestionUserAnswerRepository,
    private val userScaleRepository: UserScaleRepository
) {
    fun findAll() = scaleQuestionRepository.findAllByOrderByQuestionNumberAsc()

    fun appendUserAnswer(user: User, scaleQuestionId: Long, userAnswer: Int) {
        scaleQuestionUserAnswerRepository.save(
            ScaleQuestionUserAnswer(
                user = user,
                scaleQuestionId = scaleQuestionId,
                userAnswer = userAnswer,
                termCount = user.scaleQuestionTermCount
            )
        )
    }

    fun findAllByIds(ids: List<Long>): List<ScaleQuestion> = scaleQuestionRepository.findAllById(ids)

    fun needsScaleQuestion(user: User, termCount: Int): Boolean = !userScaleRepository.existsByUserIdAndTermCount(user.id!!, termCount / SCALE_QUESTION_INTERVAL)

    companion object {
        const val SCALE_QUESTION_INTERVAL = 8
    }
}