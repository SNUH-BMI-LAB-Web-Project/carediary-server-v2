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
}